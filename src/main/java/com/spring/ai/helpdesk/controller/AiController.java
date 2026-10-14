package com.spring.ai.helpdesk.controller;

import com.spring.ai.helpdesk.service.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/v1/helpdesk")
@RequiredArgsConstructor
public class AiController {

    private final AiService service;

    @PostMapping(value = "/response", consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> getResponse(
            @RequestBody String query,
            @RequestHeader(value = "Conversation-Id", required = false, defaultValue = "default") String conversationId) {
        return ResponseEntity.ok(service.getResponseFromAssistant(query, conversationId));
    }

    @PostMapping(value = "/stream", consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamResponse(
            @RequestBody String query,
            @RequestHeader(value = "Conversation-Id", required = false, defaultValue = "default") String conversationId) {
        return this.service.streamResponseFromAssistant(query, conversationId);
    }
}
