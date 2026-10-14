package com.spring.ai.helpdesk.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

@Component
public class EmailTool {

    @Tool(description = "Send a support email to the help desk team regarding a new or updated ticket")
    public void sendEmailToSupportTeam(
            @ToolParam(description = "Email of the user to contact") String email,
            @ToolParam(description = "Short summary or message for the support team") String message) {

        System.out.println("Going to send email to support system");
        System.out.println("Email id: " + email);
        System.out.println("Message: " + message);
    }
}
