package com.spring.ai.helpdesk.service;

import com.spring.ai.helpdesk.entity.Priority;
import com.spring.ai.helpdesk.entity.Status;
import com.spring.ai.helpdesk.entity.Ticket;
import com.spring.ai.helpdesk.repository.TicketRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private TicketService ticketService;

    @Test
    void createTicket_shouldSaveAndReturnTicket() {
        Ticket ticket = Ticket.builder()
                .email("user@example.com")
                .summary("Password reset")
                .description("User cannot log in")
                .priority(Priority.HIGH)
                .status(Status.OPEN)
                .category("Account")
                .createdOn(LocalDateTime.now())
                .build();

        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);

        Ticket saved = ticketService.createTicket(ticket);

        assertNotNull(saved);
        assertEquals("user@example.com", saved.getEmail());
        verify(ticketRepository, times(1)).save(any(Ticket.class));
    }

    @Test
    void getTicketByEmail_shouldReturnTicketWhenExists() {
        Ticket ticket = Ticket.builder()
                .id(1L)
                .email("user@example.com")
                .status(Status.IN_PROGRESS)
                .build();

        when(ticketRepository.findByEmail("user@example.com")).thenReturn(Optional.of(ticket));

        Ticket found = ticketService.getTicketByEmail("user@example.com");

        assertNotNull(found);
        assertEquals(1L, found.getId());
        assertEquals(Status.IN_PROGRESS, found.getStatus());
    }
}
