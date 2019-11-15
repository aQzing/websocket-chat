package com.qzing.websocket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {
	@RequestMapping("/serverToMany")
	private String serverToMany() {
		return "serverToMany";
	}

	@RequestMapping("/serverToOne")
	private String serverToOne() {
		return "serverToOne";
	}
	@RequestMapping("/chat")
	private String chat() {
		return "chat";
	}
}
