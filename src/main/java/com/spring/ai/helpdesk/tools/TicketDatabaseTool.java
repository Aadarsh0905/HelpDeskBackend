package com.spring.ai.helpdesk.tools;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import com.spring.ai.helpdesk.entity.Priority;
import com.spring.ai.helpdesk.entity.Status;
import com.spring.ai.helpdesk.entity.Ticket;
import com.spring.ai.helpdesk.service.TicketService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TicketDatabaseTool {

	private final TicketService ticketService;

	// Tool: Create new ticket using scalar parameters (optimal for LLM function calling)
	@Tool(description = "Create a new support ticket in the database. Returns the newly created ticket with its assigned ID.")
	public Ticket createTicket(
			@ToolParam(description = "User's email address (mandatory)") String email,
			@ToolParam(description = "Brief summary or title of the issue") String summary,
			@ToolParam(description = "Detailed explanation of the issue") String description,
			@ToolParam(description = "Urgency level: LOW, MEDIUM, HIGH, or URGENT") Priority priority,
			@ToolParam(description = "Category such as Hardware, Software, Network, Account, etc.") String category) {

		try {
			Ticket ticket = Ticket.builder()
					.email(email)
					.summary(summary)
					.description(description)
					.priority(priority != null ? priority : Priority.MEDIUM)
					.category(category != null ? category : "General")
					.status(Status.OPEN)
					.build();

			Ticket created = ticketService.createTicket(ticket);
			System.out.println("Ticket created successfully: " + created);
			return created;
		} catch (Exception e) {
			System.err.println("Error creating ticket via tool: " + e.getMessage());
			return null;
		}
	}

	// Tool: Update ticket by ticket ID
	@Tool(description = "Update the status or notes of an existing ticket by its ticket ID")
	public Ticket updateTicket(
			@ToolParam(description = "The numeric ID of the ticket") Long ticketId,
			@ToolParam(description = "The new status: OPEN, IN_PROGRESS, RESOLVED, or CLOSED") Status status,
			@ToolParam(description = "New notes or description updates to append to the ticket") String additionalNotes) {

		try {
			Ticket existing = ticketService.getTicket(ticketId);
			if (existing == null) {
				return null;
			}
			if (status != null) {
				existing.setStatus(status);
			}
			if (additionalNotes != null && !additionalNotes.isBlank()) {
				existing.setDescription(existing.getDescription() + "\n[Update " + LocalDateTime.now() + "]: " + additionalNotes);
			}
			return ticketService.updateTicket(existing);
		} catch (Exception e) {
			System.err.println("Error updating ticket via tool: " + e.getMessage());
			return null;
		}
	}

	// Tool: Get tickets by user email
	@Tool(description = "Retrieve all existing tickets submitted by a specific user email address")
	public List<Ticket> getTicketsByEmail(
			@ToolParam(description = "The email address to search tickets for") String email) {
		return ticketService.getTicketsByEmail(email);
	}

	// Tool: Get ticket by ID
	@Tool(description = "Fetch ticket details by its unique numeric ticket ID")
	public Ticket getTicketById(
			@ToolParam(description = "The ticket ID") Long ticketId) {
		return ticketService.getTicket(ticketId);
	}

	// Tool: Get all tickets
	@Tool(description = "Retrieve all tickets currently registered in the helpdesk system")
	public List<Ticket> getAllTickets() {
		return ticketService.getAllTickets();
	}

	// Tool: Get current system time
	@Tool(description = "Get the current system date and time")
	public String getCurrentTime() {
		return LocalDateTime.now().toString();
	}
}
