/**
 * @author arif.shaikh 04-Aug-2024
 */
package com.practice.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.dto.PaymentOrderDTO;
import com.practice.dto.UserDTO;
import com.practice.helper.HelperEnum.PAYMENT_METHOD;
import com.practice.service.IPaymentOrderService;
import com.practice.service.IUserService;

/**
 * 
 */
@RestController
@RequestMapping("/api")
public class PaymentOrderController {

	@Autowired
	private IUserService userService;

	@Autowired
	private IPaymentOrderService paymentOrderService;

	@PostMapping("/payment/{paymentMethod}/amount/{amount}")
	public ResponseEntity<String> paymentHandler(@RequestHeader("Authorization") String jwt,
			@PathVariable PAYMENT_METHOD paymentMethod, @PathVariable Long amount) throws Exception {
		UserDTO userDTO = userService.findUserProfileByJwt(jwt);
		PaymentOrderDTO paymentOrderDTO = paymentOrderService.createOrder(userDTO, BigDecimal.valueOf(amount),
				paymentMethod);
		String response = null;
		if (paymentMethod.equals(PAYMENT_METHOD.RAZORPAY)) {
			response = paymentOrderService.createRazorPaymentLink(userDTO, amount, paymentOrderDTO.getId());
		} else if (paymentMethod.equals(PAYMENT_METHOD.STRIPE)) {
			response = paymentOrderService.createStripePaymentLink(userDTO, amount, paymentOrderDTO.getId());
		} else {
			throw new Exception("Incorrect Payment method");
		}
		return new ResponseEntity<>(response, HttpStatus.CREATED);

	}

}
