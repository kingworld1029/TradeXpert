/**
 * @author arif.shaikh 05-Aug-2024
 */
package com.practice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.dto.UserDTO;
import com.practice.dto.WalletDTO;
import com.practice.dto.WalletTransactionDTO;
import com.practice.service.ITransactionService;
import com.practice.service.IUserService;
import com.practice.service.IWalletService;

/**
 * 
 */
@RestController
@RequestMapping("api/transactions")
public class TransactionController {
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IWalletService walletService;
	
	@Autowired
	private ITransactionService transactionService;
	
	
	@GetMapping()
	public ResponseEntity<List<WalletTransactionDTO>> getUserWalletTransactions(@RequestHeader("Authorization") String jwt)throws Exception{
		UserDTO userDTO = userService.findUserProfileByJwt(jwt);
		WalletDTO walletDTO = walletService.getUserWallet(userDTO);
		List<WalletTransactionDTO> transactionDTOList = transactionService.getTransactionByWallet(walletDTO);
		return new ResponseEntity<>(transactionDTOList,HttpStatus.ACCEPTED);
		
	}

}
