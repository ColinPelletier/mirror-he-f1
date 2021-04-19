package ch.hearc.hef1.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NotFoundController {
	@GetMapping("/notfound")
	public String notfound(Map<String, Object> model) {
		return "notfound";
	}

	@GetMapping("/error")
	public String badRequest(Map<String, Object> model) {
		return "error";
	}
}
