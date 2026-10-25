package com.spring.ai.helpdesk.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.spring.ai.helpdesk.tools.EmailTool;
import com.spring.ai.helpdesk.tools.TicketDatabaseTool;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class AiService {

	private final ChatClient chatClient;

	@Value("classpath:/helpdesk-system.st")
	private Resource systemPromptResource;

	private final TicketDatabaseTool ticketDatabaseTool;
	private final EmailTool emailTool;

	public String getResponseFromAssistant(String query, String conversationId) {
		return this.chatClient
				.prompt()
				.advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID, conversationId))
				.tools(ticketDatabaseTool, emailTool)
				.system(systemPromptResource)
				.user(query)
				.call()
				.content();
	}

	public Flux<String> streamResponseFromAssistant(String query, String conversationId) {
		return this.chatClient
				.prompt()
				.advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID, conversationId))
				.tools(ticketDatabaseTool, emailTool)
				.system(systemPromptResource)
				.user(query)
				.stream()
				.content();
	}
}
