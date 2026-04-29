package com.spring.ai.helpdesk.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import com.spring.ai.helpdesk.entity.Ticket;
import com.spring.ai.helpdesk.service.TicketService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TicketDatabaseTool {
	private final TicketService ticketService;
	
	
	//create ticket tool
	@Tool(description="This tool helps to create new ticket in database")
	public Ticket createTicket(@ToolParam(description="Ticket details")Ticket ticket) {
		
		try {
			System.out.println("Going to create the ticket");
			System.out.println(ticket);
			return ticketService.createTicket(ticket);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	//get ticket using email
	@Tool(description="This tool helps to get ticket by email")
	public Ticket getTicketByUsername(@ToolParam(description="username whose ticket is required")String email) {
		return ticketService.getTicketByEmail(email);
	}
	
	//update ticket
	@Tool(description="This tool helps to update ticket")
	public Ticket updateTicket(@ToolParam(description="new ticket details with ticket id")Ticket ticket) {
		return ticketService.updateTicket(ticket);
	}
	
	//get current system time
	@Tool(description="This tool helps to get current system time")
	public String getCurrentTime() {
		return String.valueOf(System.currentTimeMillis());
	}
	
}
