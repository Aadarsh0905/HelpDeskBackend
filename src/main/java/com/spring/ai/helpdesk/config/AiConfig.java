package com.spring.ai.helpdesk.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiConfig {

	private static final Logger logger = LoggerFactory.getLogger(AiConfig.class);

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
