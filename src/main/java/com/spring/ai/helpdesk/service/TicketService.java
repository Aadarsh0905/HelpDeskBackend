package com.spring.ai.helpdesk.service;

import com.spring.ai.helpdesk.entity.Status;
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
    private final MailService mailService;

    @Transactional
    public Ticket createTicket(Ticket ticket) {
        if (ticket == null) {
            throw new IllegalArgumentException("Ticket cannot be null");
        }
        ticket.setId(null);
        ticket.setStatus(ticket.getStatus() == null ? Status.OPEN : ticket.getStatus());
        Ticket saved = ticketRepository.save(ticket);

        if (saved.getEmail() != null && !saved.getEmail().isBlank()) {
            mailService.sendTicketEmail(
                    saved.getEmail(),
                    "Ticket Created: " + saved.getId(),
                    "Your ticket has been created successfully. Ticket ID: " + saved.getId() + "\nStatus: " + saved.getStatus()
            );
        }
        return saved;
    }

    @Transactional
    public Ticket updateTicket(Ticket ticket) {
        if (ticket == null || ticket.getId() == null) {
            throw new IllegalArgumentException("Ticket id is required for update");
        }
        Ticket updated = ticketRepository.save(ticket);

        if (updated.getEmail() != null && !updated.getEmail().isBlank()) {
            mailService.sendTicketEmail(
                    updated.getEmail(),
                    "Ticket Updated: " + updated.getId(),
                    "Your ticket has been updated successfully. Ticket ID: " + updated.getId() + "\nStatus: " + updated.getStatus()
            );
        }
        return updated;
    }

    @Transactional
    public Ticket updateStatus(Long id, Status status) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ticket not found with id: " + id));
        ticket.setStatus(status);
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
