package com.spring.ai.helpdesk.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;


@Component
public class EmailTool {
	//send the email to support team regarding new ticket
	@Tool(description="This tool helps to send email to supportteam regarding new ticket")
	public void sendEmailToSupportTeam(@ToolParam(description="Email id associated with ticket for contact information")String email,@ToolParam(description="short description of ticket summary")String message) {
		System.out.println("Going to send email to support system");
		System.out.println("Email id: "+email);
		System.out.println("Message: "+message);
	}
}
