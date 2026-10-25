package com.spring.ai.helpdesk.controller;

import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.ai.helpdesk.dto.ChatRequest;
import com.spring.ai.helpdesk.dto.ChatResponse;
import com.spring.ai.helpdesk.service.AiService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/v1/helpdesk")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AiController {

	private final AiService service;

	/**
	 * JSON-based endpoint designed for the React frontend.
	 * Request Body: { "message": "My keyboard is not working", "conversationId": "..." }
	 * Response Body: { "response": "...", "conversationId": "..." }
	 */
	@PostMapping(value = "/chat", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ChatResponse> chat(@RequestBody ChatRequest request) {
		String convId = (request.getConversationId() != null && !request.getConversationId().isBlank())
				? request.getConversationId()
				: UUID.randomUUID().toString();

		String query = request.getMessage() != null ? request.getMessage() : "";
		String assistantReply = service.getResponseFromAssistant(query, convId);

		return ResponseEntity.ok(new ChatResponse(assistantReply, convId));
	}

	/**
	 * Raw text endpoint (for Postman or direct curl requests).
	 * Supports Conversation-Id header, conversationId query param, or auto-generates one.
	 */
	@PostMapping(value = "/response", consumes = { MediaType.TEXT_PLAIN_VALUE, MediaType.ALL_VALUE })
	public ResponseEntity<String> getResponse(
			@RequestBody String query,
			@RequestHeader(value = "Conversation-Id", required = false) String conversationIdHeader,
			@RequestHeader(value = "Conversation Id", required = false) String legacyHeader,
			@RequestParam(value = "conversationId", required = false) String conversationIdParam) {

		String convId = conversationIdHeader != null && !conversationIdHeader.isBlank()
				? conversationIdHeader
				: (legacyHeader != null && !legacyHeader.isBlank()
						? legacyHeader
						: (conversationIdParam != null && !conversationIdParam.isBlank()
								? conversationIdParam
								: UUID.randomUUID().toString()));

		return ResponseEntity.ok(service.getResponseFromAssistant(query, convId));
	}

	/**
	 * Streaming response endpoint (SSE).
	 */
	@PostMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<String> streamResponse(
			@RequestBody String query,
			@RequestHeader(value = "Conversation-Id", required = false) String conversationIdHeader,
			@RequestHeader(value = "Conversation Id", required = false) String legacyHeader,
			@RequestParam(value = "conversationId", required = false) String conversationIdParam) {

		String convId = conversationIdHeader != null && !conversationIdHeader.isBlank()
				? conversationIdHeader
				: (legacyHeader != null && !legacyHeader.isBlank()
						? legacyHeader
						: (conversationIdParam != null && !conversationIdParam.isBlank()
								? conversationIdParam
								: UUID.randomUUID().toString()));

		return this.service.streamResponseFromAssistant(query, convId);
	}
}
