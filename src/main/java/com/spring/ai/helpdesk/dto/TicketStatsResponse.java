package com.spring.ai.helpdesk.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketStatsResponse {
	private long totalTickets;
	private long openTickets;
	private long inProgressTickets;
	private long resolvedTickets;
	private long closedTickets;
}
