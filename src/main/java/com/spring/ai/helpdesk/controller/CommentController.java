package com.spring.ai.helpdesk.controller;

import com.spring.ai.helpdesk.entity.TicketComment;
import com.spring.ai.helpdesk.entity.User;
import com.spring.ai.helpdesk.dto.CommentRequest;
import com.spring.ai.helpdesk.service.TicketCommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tickets")
@RequiredArgsConstructor
public class CommentController {

    private final TicketCommentService ticketCommentService;

    @PostMapping("/{ticketId}/comments")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<TicketComment> addComment(
            @PathVariable Long ticketId,
            @Valid @RequestBody CommentRequest request,
            @AuthenticationPrincipal User currentUser) {
        return new ResponseEntity<>(
                ticketCommentService.addComment(ticketId, currentUser.getEmail(), request.getMessage()),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{ticketId}/comments")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public List<TicketComment> getComments(@PathVariable Long ticketId) {
        return ticketCommentService.getComments(ticketId);
    }
}
