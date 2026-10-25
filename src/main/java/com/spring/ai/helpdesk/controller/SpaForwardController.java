package com.spring.ai.helpdesk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SpaForwardController {

	// Forward non-API client routes to index.html so React can handle client-side state
	@GetMapping(value = {
			"/",
			"/chat",
			"/dashboard",
			"/tickets",
			"/create"
	})
	public String forwardClientRoutes() {
		return "forward:/index.html";
	}
}
