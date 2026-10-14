package com.spring.ai.helpdesk.tools;

import com.spring.ai.helpdesk.entity.Ticket;
import com.spring.ai.helpdesk.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TicketDatabaseTool {

    private final TicketService ticketService;

    @Tool(description = "Create a new help desk ticket in the database")
    public Ticket createTicket(@ToolParam(description = "Ticket details") Ticket ticket) {
        try {
            return ticketService.createTicket(ticket);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Tool(description = "Find a ticket by the user's email address")
    public Ticket getTicketByEmail(@ToolParam(description = "Email address of the ticket owner") String email) {
        return ticketService.getTicketByEmail(email);
    }

    @Tool(description = "Update an existing ticket")
    public Ticket updateTicket(@ToolParam(description = "Updated ticket data with id") Ticket ticket) {
        return ticketService.updateTicket(ticket);
    }

    @Tool(description = "Return the current system time")
    public String getCurrentTime() {
        return String.valueOf(System.currentTimeMillis());
    }
}
