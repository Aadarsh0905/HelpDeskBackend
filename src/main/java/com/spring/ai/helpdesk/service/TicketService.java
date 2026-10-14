package com.spring.ai.helpdesk.service;

import com.spring.ai.helpdesk.entity.Ticket;
import com.spring.ai.helpdesk.repository.TicketRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;

    @Transactional
    public Ticket createTicket(Ticket ticket) {
        if (ticket == null) {
            throw new IllegalArgumentException("Ticket cannot be null");
        }
        ticket.setId(null);
        return ticketRepository.save(ticket);
    }

    @Transactional
    public Ticket updateTicket(Ticket ticket) {
        if (ticket == null || ticket.getId() == null) {
            throw new IllegalArgumentException("Ticket id is required for update");
        }
        return ticketRepository.save(ticket);
    }

    public Ticket getTicket(Long id) {
        return ticketRepository.findById(id).orElse(null);
    }

    public Ticket getTicketByEmail(String email) {
        return ticketRepository.findByEmail(email).orElse(null);
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }
}
