/**
 * @author arif.shaikh 30-Jul-2024
 */
package com.practice.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.practice.dto.AuthResponse;
import com.practice.dto.ForgotPasswordTokenDTO;
import com.practice.dto.UserDTO;
import com.practice.dto.VerificationCodeDTO;
import com.practice.helper.HelperEnum.VERIFICATION_TYPE;
import com.practice.service.IEmailService;
import com.practice.service.IForgotPasswordService;
import com.practice.service.IUserService;
import com.practice.service.IVerificationCodeService;
import com.practice.utils.OtpUtility;

/**
 * 
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private IUserService userService;

	@Autowired
	private IVerificationCodeService verificationCodeService;

	@Autowired
	private IEmailService emailService;

	@GetMapping("/profile")
	public ResponseEntity<UserDTO> getUserProfile(@RequestHeader("Authorization") String jwt) throws Exception {
		UserDTO userDTO = userService.findUserProfileByJwt(jwt);
		return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);

	}

	@PostMapping("/sendVerificationOtp/{verificationType}")
	public ResponseEntity<String> sendVerificationOtp(@RequestHeader("Authorization") String jwt,
			@PathVariable VERIFICATION_TYPE verificationType) throws Exception {

		UserDTO userDTO = userService.findUserProfileByJwt(jwt);
		VerificationCodeDTO verificationCodeDTO = verificationCodeService.getVerificationCodeByUser(userDTO.getId());
		if (verificationCodeDTO == null) {
			verificationCodeDTO = verificationCodeService.sendveificationCode(userDTO, verificationType);
		}
		if (verificationType.equals(VERIFICATION_TYPE.EMAIL)) {
			emailService.sendVerificationOtpEmail(userDTO.getEmail(), verificationCodeDTO.getOtp());
		}
		return new ResponseEntity<>("Verification Otp Sent SuccessFully", HttpStatus.OK);

	}

	@PatchMapping("/enable-two-factor/{otp}")
	public ResponseEntity<UserDTO> enableTwoFactorAuthentication(@PathVariable String otp,
			@RequestHeader("Authorization") String jwt) throws Exception {
		UserDTO userDTO = userService.findUserProfileByJwt(jwt);
		VerificationCodeDTO verificationCodeDTO = verificationCodeService.getVerificationCodeByUser(userDTO.getId());
		String sendTo = verificationCodeDTO.getVerificationType().equals(VERIFICATION_TYPE.EMAIL)
				? verificationCodeDTO.getEmail()
				: verificationCodeDTO.getMobile();
		boolean isVerified = verificationCodeDTO.getOtp().equals(otp);
		if (isVerified) {
			UserDTO updateUserDTO = userService.enableTwoFactorAuthentocation(verificationCodeDTO.getVerificationType(),
					sendTo, userDTO);
			verificationCodeService.deleteVerificationCode(verificationCodeDTO);
			return new ResponseEntity<UserDTO>(updateUserDTO, HttpStatus.OK);

		}
		throw new Exception("Wrong OTP");

	}

}
