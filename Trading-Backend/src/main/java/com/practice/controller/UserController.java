/**
 * @author arif.shaikh 30-Jul-2024
 */
package com.practice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.practice.dto.UserDTO;
import com.practice.helper.HelperEnum.VERIFICATION_TYPE;
import com.practice.service.IEmailService;
import com.practice.service.IUserService;

/**
 * 
 */
@RestController
public class UserController {

	@Autowired
	private IUserService userService;
	
	@Autowired
	private IEmailService emailService;
	
	@GetMapping("/api/users/profile")
	public ResponseEntity<UserDTO> getUserProfile(@RequestHeader("Authorization") String jwt) throws Exception{
		UserDTO userDTO  = userService.findUserProfileByJwt(jwt);
		return new ResponseEntity<UserDTO>(userDTO,HttpStatus.OK);
		
	}
	
	@PostMapping("/api/users/sendVerificationOtp")
	public ResponseEntity<UserDTO> sendVerificationOtp(@RequestHeader("Authorization") String jwt,
			@PathVariable VERIFICATION_TYPE verificationType) throws Exception{
		UserDTO userDTO  = userService.findUserProfileByJwt(jwt);
		return new ResponseEntity<UserDTO>(userDTO,HttpStatus.OK);
		
	}

	@PatchMapping("/api/users/enable-two-factor")
	public ResponseEntity<UserDTO> enableTwoFactorAuthentication(@RequestHeader("Authorization") String jwt) throws Exception{
		UserDTO userDTO  = userService.findUserProfileByJwt(jwt);
		return new ResponseEntity<UserDTO>(userDTO,HttpStatus.OK);
		
	}
}
