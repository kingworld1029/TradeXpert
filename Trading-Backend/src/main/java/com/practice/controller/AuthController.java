/**
 * @author arif.shaikh 16-Jun-2024
 */
package com.practice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.practice.dto.AuthResponse;
import com.practice.dto.ForgotPasswordTokenDTO;
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

	@PostMapping("/verifySigningOtp/otp/{otp}")
	public ResponseEntity<AuthResponse> verifySigningOtp(@PathVariable String otp, @RequestParam String id)
			throws Exception {
		AuthResponse response = userService.verifySigningOtp(otp, id);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/sendForgotPasswordOtp")
	public ResponseEntity<AuthResponse> sendForgotPasswordOtp(@RequestBody ForgotPasswordTokenDTO requestDTO)
			throws Exception {

		AuthResponse response = userService.sendForgotPasswordOtp(requestDTO);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@PatchMapping("/verifyForgotPasswordOtp")
	public ResponseEntity<AuthResponse> verifyForgotPasswordOtp(@RequestBody ForgotPasswordTokenDTO requestDTO,
			@RequestParam String id) throws Exception {

		AuthResponse response = userService.verifyForgotPasswordOtp(requestDTO, id);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

}
