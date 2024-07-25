/**
 * @author arif.shaikh 16-Jun-2024
 */
package com.practice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.practice.dto.AuthResponse;
import com.practice.dto.UserDTO;
import com.practice.service.IUserService;

/**
 * 
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private IUserService userService;

	@PostMapping("/register")
	public ResponseEntity<AuthResponse> register(@RequestBody UserDTO userDTO) throws Exception {
		AuthResponse response = userService.register(userDTO);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@RequestBody UserDTO userDTO) throws Exception {
		AuthResponse response = userService.login(userDTO);
		return new ResponseEntity<>(response, HttpStatus.ACCEPTED);

	}
	
}
