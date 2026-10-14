package com.spring.ai.helpdesk.service;

import com.spring.ai.helpdesk.entity.Ticket;
import com.spring.ai.helpdesk.entity.TicketComment;
import com.spring.ai.helpdesk.entity.User;
import com.spring.ai.helpdesk.repository.TicketCommentRepository;
import com.spring.ai.helpdesk.repository.TicketRepository;
import com.spring.ai.helpdesk.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketCommentService {

    private final TicketCommentRepository commentRepository;
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    public TicketComment addComment(Long ticketId, String email, String message) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new IllegalArgumentException("Ticket not found with id: " + ticketId));

        User author = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + email));

        TicketComment comment = TicketComment.builder()
                .ticket(ticket)
                .author(author)
                .message(message)
                .build();

        return commentRepository.save(comment);
    }

    public List<TicketComment> getComments(Long ticketId) {
        return commentRepository.findByTicketIdOrderByCreatedAtAsc(ticketId);
    }
}
