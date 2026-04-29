package com.spring.ai.helpdesk.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.ai.helpdesk.service.AiService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/helpdesk")
@RequiredArgsConstructor
public class AiController {
	private final AiService service;
	
	@PostMapping("/response")
	public ResponseEntity<String> getResponse(@RequestBody String query,@RequestHeader("Conversation Id")String conversationId){
		return ResponseEntity.ok(service.getResponseFromAssistant(query,conversationId));
	}
	
	@PostMapping("/stream")
	public Flux<String> streamResponse(@RequestBody String query,@RequestHeader("Conversation Id")String conversationId){
		return this.service.streamResponseFromAssistant(query, conversationId);
	}
}

//in body of postman->raw->text write the text

