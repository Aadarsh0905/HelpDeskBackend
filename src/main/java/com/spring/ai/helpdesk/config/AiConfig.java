package com.spring.ai.helpdesk.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;

@Configuration
public class AiConfig {
	
	private Logger logger=LoggerFactory.getLogger(AiConfig.class);
	
//	@Bean
//	public  ChatClient chatClient(ChatClient.Builder builder,ChatMemory chatMemory) {
//		logger.info("ChatClient bean created");
//		logger.info("Chat memory bean created.{}",chatMemory.getClass().getName());
//		
//		return builder
//				.defaultSystem("Summarize the response within 400 words")
//				.defaultAdvisors(new SimpleLoggerAdvisor(),MessageChatMemoryAdvisor.builder(chatMemory).build())
//				.build();
//	}
	
	@Bean
	public  ChatClient chatClient(ChatClient.Builder builder,JdbcChatMemoryRepository jdbcChatMemoryRepository) {
		//chat memory ko create kr skte h
		var chatMemory=MessageWindowChatMemory.builder()
				.chatMemoryRepository(jdbcChatMemoryRepository)
				.maxMessages(14)
				.build();
		
		
		
		
		
		logger.info("ChatClient bean created");
		logger.info("Chat memory bean created.{}",chatMemory.getClass().getName());
		
		return builder
				.defaultSystem("Summarize the response within 400 words")
				.defaultAdvisors(new SimpleLoggerAdvisor(),MessageChatMemoryAdvisor.builder(chatMemory).build())
				.build();
	}
	
}
