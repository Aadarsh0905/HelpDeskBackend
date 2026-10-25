package com.spring.ai.helpdesk.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.ai.helpdesk.dto.TicketStatsResponse;
import com.spring.ai.helpdesk.entity.Priority;
import com.spring.ai.helpdesk.entity.Status;
import com.spring.ai.helpdesk.entity.Ticket;
import com.spring.ai.helpdesk.repository.TicketRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TicketService {

	private final TicketRepository ticketRepository;

	// Create new ticket
	@Transactional
	public Ticket createTicket(Ticket ticket) {
		ticket.setId(null);
		return ticketRepository.save(ticket);
	}

	// Update ticket
	@Transactional
	public Ticket updateTicket(Ticket ticket) {
		return ticketRepository.save(ticket);
	}

	// Update ticket status
	@Transactional
	public Ticket updateTicketStatus(Long ticketId, Status status) {
		Ticket ticket = getTicket(ticketId);
		if (ticket != null) {
			ticket.setStatus(status);
			return ticketRepository.save(ticket);
		}
		return null;
	}

	// Get ticket by ID
	public Ticket getTicket(Long ticketId) {
		return ticketRepository.findById(ticketId).orElse(null);
	}

	// Get all tickets
	public List<Ticket> getAllTickets() {
		return ticketRepository.findAll();
	}

	// Get tickets by email
	public List<Ticket> getTicketsByEmail(String email) {
		return ticketRepository.findByEmailOrderByCreatedOnDesc(email);
	}

	// Get latest ticket by email
	public Ticket getLatestTicketByEmail(String email) {
		return ticketRepository.findFirstByEmailOrderByCreatedOnDesc(email).orElse(null);
	}

	// Get tickets by status
	public List<Ticket> getTicketsByStatus(Status status) {
		return ticketRepository.findByStatusOrderByCreatedOnDesc(status);
	}

	// Get tickets by priority
	public List<Ticket> getTicketsByPriority(Priority priority) {
		return ticketRepository.findByPriorityOrderByCreatedOnDesc(priority);
	}

	// Delete ticket
	@Transactional
	public boolean deleteTicket(Long ticketId) {
		if (ticketRepository.existsById(ticketId)) {
			ticketRepository.deleteById(ticketId);
			return true;
		}
		return false;
	}

	// Get ticket stats for dashboard
	public TicketStatsResponse getTicketStats() {
		long total = ticketRepository.count();
		long open = ticketRepository.countByStatus(Status.OPEN);
		long inProgress = ticketRepository.countByStatus(Status.IN_PROGRESS);
		long resolved = ticketRepository.countByStatus(Status.RESOLVED);
		long closed = ticketRepository.countByStatus(Status.CLOSED);

		return TicketStatsResponse.builder()
				.totalTickets(total)
				.openTickets(open)
				.inProgressTickets(inProgress)
				.resolvedTickets(resolved)
				.closedTickets(closed)
				.build();
	}
}
