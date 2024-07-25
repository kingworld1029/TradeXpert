/**
 * @author arif.shaikh 16-Jun-2024
 */
package com.practice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
	
	@GetMapping()
	public String home() {
		return "Welcome to Trading App..";
	}

}
