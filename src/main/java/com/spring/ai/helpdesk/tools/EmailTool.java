package com.spring.ai.helpdesk.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

@Component
public class EmailTool {

	@Tool(description = "This tool sends an email notification to the support team regarding a newly created or updated ticket")
	public String sendEmailToSupportTeam(
			@ToolParam(description = "User's email address associated with the ticket") String email,
			@ToolParam(description = "Summary message or details to include in the notification email") String message) {
		System.out.println("========== [SUPPORT EMAIL NOTIFICATION] ==========");
		System.out.println("Recipient: support-team@substringtechnologies.com");
		System.out.println("User Email: " + email);
		System.out.println("Content: " + message);
		System.out.println("==================================================");
		return "Notification successfully dispatched to the support team for " + email;
	}
}
