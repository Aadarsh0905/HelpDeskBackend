package com.spring.ai.helpdesk.config;

import java.net.URI;
import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.netty.http.client.HttpClient;

@Configuration
public class AiConfig {

	private static final Logger logger = LoggerFactory.getLogger(AiConfig.class);

	@Bean
	public OllamaApi ollamaApi(@Value("${spring.ai.ollama.base-url:http://localhost:11434}") String baseUrl) {
		String cleanUrl = (baseUrl != null) ? baseUrl.trim() : "http://localhost:11434";

		// Strip surrounding quotes if pasted with quotes
		if (cleanUrl.startsWith("\"") && cleanUrl.endsWith("\"") && cleanUrl.length() > 2) {
			cleanUrl = cleanUrl.substring(1, cleanUrl.length() - 1).trim();
		}

		// Handle accidental Render UI placeholder text
		if (cleanUrl.equalsIgnoreCase("Value") || cleanUrl.equalsIgnoreCase("Enter value") || cleanUrl.isBlank()) {
			cleanUrl = "http://localhost:11434";
		}

		// Ensure scheme is present
		if (!cleanUrl.startsWith("http://") && !cleanUrl.startsWith("https://")) {
			cleanUrl = "https://" + cleanUrl;
		}

		// Strip trailing slashes which trigger 'Bad authority' in Spring 7 URI parser
		while (cleanUrl.endsWith("/")) {
			cleanUrl = cleanUrl.substring(0, cleanUrl.length() - 1);
		}

		// Verify URI syntax
		try {
			URI.create(cleanUrl);
		} catch (Exception e) {
			logger.warn("Malformed Ollama base-url '{}', falling back to safe default.", cleanUrl);
			cleanUrl = "http://localhost:11434";
		}

		logger.info("Initializing OllamaApi with sanitized baseUrl: {}", cleanUrl);

		JdkClientHttpRequestFactory requestFactory = new JdkClientHttpRequestFactory();
		requestFactory.setReadTimeout(Duration.ofMinutes(5));
		RestClient.Builder restClientBuilder = RestClient.builder().requestFactory(requestFactory);

		HttpClient httpClient = HttpClient.create()
				.responseTimeout(Duration.ofMinutes(5));
		WebClient.Builder webClientBuilder = WebClient.builder()
				.clientConnector(new ReactorClientHttpConnector(httpClient));

		return OllamaApi.builder()
				.baseUrl(cleanUrl)
				.restClientBuilder(restClientBuilder)
				.webClientBuilder(webClientBuilder)
				.build();
	}

	@Bean
	public ChatClient chatClient(ChatClient.Builder builder, JdbcChatMemoryRepository jdbcChatMemoryRepository) {
		var chatMemory = MessageWindowChatMemory.builder()
				.chatMemoryRepository(jdbcChatMemoryRepository)
				.maxMessages(20)
				.build();

		logger.info("ChatClient created with JDBC Chat Memory and SimpleLoggerAdvisor");

		return builder
				.defaultAdvisors(
						new SimpleLoggerAdvisor(),
						MessageChatMemoryAdvisor.builder(chatMemory).build()
				)
				.build();
	}
}
