/**
   * @author arif.shaikh 03-Aug-2024
 */
package com.practice.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.practice.dto.OrderDTO;
import com.practice.dto.PaymentOrderDTO;
import com.practice.dto.UserDTO;
import com.practice.dto.WalletDTO;
import com.practice.dto.WalletTransactionDTO;
import com.practice.service.IOrderService;
import com.practice.service.IPaymentOrderService;
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

	@Autowired
	private IPaymentOrderService paymentOrderService;

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
				BigDecimal.valueOf(walletTransactionDTO.getAmount()));
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

	@PutMapping("/deposit")
	public ResponseEntity<WalletDTO> addMoneyToWallet(@RequestHeader("Authorization") String jwt,
			@RequestParam("order_id") Long orderId, @RequestParam("payment_id") String paymnetId) throws Exception {
		UserDTO userDTO = userService.findUserProfileByJwt(jwt);
		WalletDTO walletDTO = walletService.getUserWallet(userDTO);
		PaymentOrderDTO paymentOrderDTO = paymentOrderService.getPaymentOrderById(orderId);
		Boolean status = paymentOrderService.proceedPaymentOrder(paymentOrderDTO, paymnetId);
		if (status) {
			walletDTO = walletService.addBalance(walletDTO, paymentOrderDTO.getAmount());
		}
		return new ResponseEntity<>(walletDTO, HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/transactions")
	public ResponseEntity<List<WalletTransactionDTO>> getUserWalletTransactions(
			@RequestHeader("Authorization") String jwt) throws Exception {
		UserDTO userDTO = userService.findUserProfileByJwt(jwt);
		WalletDTO walletDTO = walletService.getUserWallet(userDTO);
		List<WalletTransactionDTO> transactionDTOList = walletService.getTransactionByWallet(walletDTO);
		return new ResponseEntity<>(transactionDTOList, HttpStatus.ACCEPTED);

	}

}
