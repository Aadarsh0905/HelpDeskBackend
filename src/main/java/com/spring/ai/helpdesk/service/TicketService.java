package com.spring.ai.helpdesk.service;

import org.springframework.stereotype.Service;

import com.spring.ai.helpdesk.entity.Ticket;
import com.spring.ai.helpdesk.repository.TicketRepository;

import jakarta.transaction.Transactional;
import lombok.*;

@Service
@Data
@RequiredArgsConstructor
public class TicketService {
	private TicketRepository ticketRepository;
	
	
	//create ticket
	@Transactional
	public Ticket createTicket(Ticket ticket) {
		ticket.setId(null);
		return ticketRepository.save(ticket);
	}
	
	//update ticket
	public Ticket updateTicket(Ticket ticket) {
		return ticketRepository.save(ticket);
	}
	
	//get ticket
	public Ticket getTicket(Long TicketId) {
		return ticketRepository.findById(TicketId).orElse(null);
	}
	
	//get ticket by email
	public Ticket getTicketByEmail(String email) {
		return ticketRepository.findByEmail(email).orElse(null);
	}
	
	
}
