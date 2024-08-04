/**
 * @author arif.shaikh 04-Aug-2024
 */
package com.practice.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.dto.UserDTO;
import com.practice.dto.WalletDTO;
import com.practice.dto.WalletTransactionDTO;
import com.practice.dto.WithdrawalDTO;
import com.practice.helper.HelperEnum.WALLET_TRANS_TYPE;
import com.practice.service.IUserService;
import com.practice.service.IWalletService;
import com.practice.service.IWithdrawalService;

/**
 * 
 */
@RestController
@RequestMapping("/api")
public class WithdrawalController {

	@Autowired
	private IWithdrawalService withdrawalService;

	@Autowired
	private IWalletService walletService;

	@Autowired
	private IUserService userService;

	@PostMapping("/withdrawal/{amount}")
	public ResponseEntity<?> withdrawalReqeust(@RequestHeader("Authorization") String jwt, @PathVariable Long amount)
			throws Exception {
		UserDTO userDTO = userService.findUserProfileByJwt(jwt);
		WalletDTO walletDTO = walletService.getUserWallet(userDTO);
		WithdrawalDTO withdrawalDTO = withdrawalService.requestWithdrawal(BigDecimal.valueOf(amount), userDTO);
		walletService.addBalance(walletDTO, BigDecimal.valueOf(-amount));
//		WalletTransactionDTO walletTransactionDTO = walletTransactionService.createTransaction(userDTO,
//				WALLET_TRANS_TYPE.WITHDRAWAL, null, "bank account withdrawal", withdrawalDTO.getAmount());
		return new ResponseEntity<>(withdrawalDTO, HttpStatus.OK);
	}

	@PatchMapping("admin/withdrawal/{id}/proceed/{accept}")
	public ResponseEntity<?> proceedWithdrawal(@RequestHeader("Authorization") String jwt, @PathVariable Long id,
			@PathVariable boolean accept) throws Exception {
		UserDTO userDTO = userService.findUserProfileByJwt(jwt);
		WalletDTO walletDTO = walletService.getUserWallet(userDTO);
		WithdrawalDTO withdrawalDTO = withdrawalService.proceedWithWithdrawal(id, accept);
		if (!accept) {
			walletService.addBalance(walletDTO, withdrawalDTO.getAmount());
		}
		return new ResponseEntity<>(withdrawalDTO, HttpStatus.OK);

	}

	@GetMapping("/withdrawal")
	public ResponseEntity<List<WithdrawalDTO>> getUserWithdrawalHistory(@RequestHeader("Authorization") String jwt)
			throws Exception {
		UserDTO userDTO = userService.findUserProfileByJwt(jwt);
		List<WithdrawalDTO> withdrawalDTOList = withdrawalService.getUserWithdrawalHistory(userDTO);
		return new ResponseEntity<>(withdrawalDTOList, HttpStatus.OK);

	}

	@GetMapping("/admin/withdrawal")
	public ResponseEntity<List<WithdrawalDTO>> getAllWithdrawalRequest(@RequestHeader("Authorization") String jwt)
			throws Exception {
		UserDTO userDTO = userService.findUserProfileByJwt(jwt);
		List<WithdrawalDTO> withdrawalDTOList = withdrawalService.getAllWithdrawalRequest();
		return new ResponseEntity<>(withdrawalDTOList, HttpStatus.OK);

	}

}
