/**
 * @author arif.shaikh 04-Aug-2024
 */
package com.practice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.dto.PaymentDetailDTO;
import com.practice.dto.UserDTO;
import com.practice.service.IPaymentDetailService;
import com.practice.service.IUserService;

/**
 * 
 */
@RestController
@RequestMapping("/api")
public class PaymentDetailController {

	@Autowired
	private IUserService userService;

	@Autowired
	private IPaymentDetailService paymentDetailService;

	@PostMapping("/addPaymentDetails")
	public ResponseEntity<PaymentDetailDTO> addPaymentDetails(@RequestBody PaymentDetailDTO requestPaymentDetailDTO,
			@RequestHeader("Authorization") String jwt) throws Exception {
		UserDTO userDTO = userService.findUserProfileByJwt(jwt);
		PaymentDetailDTO paymentDetailDTO = paymentDetailService.addPaymnetDetail(
				requestPaymentDetailDTO.getAccounNumber(), requestPaymentDetailDTO.getAccountHolderName(),
				requestPaymentDetailDTO.getIfsc(), requestPaymentDetailDTO.getBankName(), userDTO);
		return new ResponseEntity<>(paymentDetailDTO, HttpStatus.CREATED);
	}

	@PostMapping("/getUserPaymentDetail")
	public ResponseEntity<PaymentDetailDTO> getUserPaymentDetail(@RequestHeader("Authorization") String jwt)
			throws Exception {
		UserDTO userDTO = userService.findUserProfileByJwt(jwt);
		PaymentDetailDTO paymentDetailDTO = paymentDetailService.getUserPaymentDetail(userDTO);
		return new ResponseEntity<>(paymentDetailDTO, HttpStatus.CREATED);
	}

}
