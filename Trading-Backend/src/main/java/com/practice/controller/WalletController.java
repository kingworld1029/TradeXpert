/**
 * @author arif.shaikh 03-Aug-2024
 */
package com.practice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.dto.OrderDTO;
import com.practice.dto.UserDTO;
import com.practice.dto.WalletDTO;
import com.practice.dto.WalletTransactionDTO;
import com.practice.service.IOrderService;
import com.practice.service.IUserService;
import com.practice.service.IWalletService;

/**
 * 
 */
@RestController
@RequestMapping("/api/wallet")
public class WalletController {

	@Autowired
	private IWalletService walletService;

	@Autowired
	private IUserService userService;

	@Autowired
	private IOrderService orderService;

	@GetMapping
	public ResponseEntity<WalletDTO> getUserWallet(@RequestHeader("Authorization") String jwt) throws Exception {
		UserDTO userDTO = userService.findUserProfileByJwt(jwt);
		WalletDTO walletDTO = walletService.getUserWallet(userDTO);
		return new ResponseEntity<>(walletDTO, HttpStatus.ACCEPTED);

	}

	@PutMapping("/walletToWalletTransfer/{walletId}")
	public ResponseEntity<WalletDTO> walletToWalletTransfer(@RequestHeader("Authorization") String jwt,
			@PathVariable Long walletId, @RequestBody WalletTransactionDTO walletTransactionDTO) throws Exception {
		UserDTO senderUserDTO = userService.findUserProfileByJwt(jwt);
		WalletDTO receiverWalletDTO = walletService.findById(walletId);
		WalletDTO walletDTO = walletService.walletToWalletTransfer(senderUserDTO, receiverWalletDTO,
				walletTransactionDTO.getAmount());
		return new ResponseEntity<>(walletDTO, HttpStatus.ACCEPTED);
	}

	@PutMapping("/order/{orderId}")
	public ResponseEntity<WalletDTO> payOrderpayment(@RequestHeader("Authorization") String jwt,
			@PathVariable Long orderId) throws Exception {
		UserDTO userDTO = userService.findUserProfileByJwt(jwt);
		OrderDTO orderDTO = orderService.getOrderById(orderId);
		WalletDTO walletDTO = walletService.payOrderpayment(orderDTO, userDTO);
		return new ResponseEntity<>(walletDTO, HttpStatus.ACCEPTED);
	}

}