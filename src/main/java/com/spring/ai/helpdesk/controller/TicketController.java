package com.spring.ai.helpdesk.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.ai.helpdesk.dto.TicketStatsResponse;
import com.spring.ai.helpdesk.entity.Status;
import com.spring.ai.helpdesk.entity.Ticket;
import com.spring.ai.helpdesk.service.TicketService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/tickets")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TicketController {

	private final TicketService ticketService;

	// List all tickets
	@GetMapping
	public ResponseEntity<List<Ticket>> getAllTickets(
			@RequestParam(value = "status", required = false) Status status) {
		if (status != null) {
			return ResponseEntity.ok(ticketService.getTicketsByStatus(status));
		}
		return ResponseEntity.ok(ticketService.getAllTickets());
	}

	// Get dashboard statistics
	@GetMapping("/stats")
	public ResponseEntity<TicketStatsResponse> getTicketStats() {
		return ResponseEntity.ok(ticketService.getTicketStats());
	}

	// Get ticket by ID
	@GetMapping("/{id}")
	public ResponseEntity<Ticket> getTicketById(@PathVariable("id") Long id) {
		Ticket ticket = ticketService.getTicket(id);
		if (ticket == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(ticket);
	}

	// Get tickets by user email
	@GetMapping("/user")
	public ResponseEntity<List<Ticket>> getTicketsByEmail(@RequestParam("email") String email) {
		return ResponseEntity.ok(ticketService.getTicketsByEmail(email));
	}

	// Create ticket manually from frontend
	@PostMapping
	public ResponseEntity<Ticket> createTicket(@RequestBody Ticket ticket) {
		Ticket created = ticketService.createTicket(ticket);
		return ResponseEntity.status(HttpStatus.CREATED).body(created);
	}

	// Update existing ticket
	@PutMapping("/{id}")
	public ResponseEntity<Ticket> updateTicket(
			@PathVariable("id") Long id,
			@RequestBody Ticket ticket) {
		ticket.setId(id);
		Ticket updated = ticketService.updateTicket(ticket);
		return ResponseEntity.ok(updated);
	}

	// Update ticket status
	@PatchMapping("/{id}/status")
	public ResponseEntity<Ticket> updateStatus(
			@PathVariable("id") Long id,
			@RequestBody Map<String, String> body) {
		String statusStr = body.get("status");
		if (statusStr == null) {
			return ResponseEntity.badRequest().build();
		}
		try {
			Status status = Status.valueOf(statusStr.toUpperCase());
			Ticket updated = ticketService.updateTicketStatus(id, status);
			if (updated == null) {
				return ResponseEntity.notFound().build();
			}
			return ResponseEntity.ok(updated);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().build();
		}
	}

	// Delete ticket
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteTicket(@PathVariable("id") Long id) {
		boolean deleted = ticketService.deleteTicket(id);
		if (deleted) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}
}
