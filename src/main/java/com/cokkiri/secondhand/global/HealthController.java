package com.cokkiri.secondhand.global;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class HealthController {
	@GetMapping("/health_was")
	public String healthCheckWas() {
		log.info("Health!");
		return "Health!";
	}
}
