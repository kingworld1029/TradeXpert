/**
 * @author arif.shaikh 03-Aug-2024
 */
package com.practice.service;

import java.math.BigDecimal;

import com.practice.dto.UserDTO;
import com.practice.dto.WalletTransactionDTO;
import com.practice.helper.HelperEnum.WALLET_TRANS_TYPE;

/**
 * 
 */
public interface IWalletTransactionService {

	/**
	 * @param userDTO
	 * @param withdrawal
	 * @param object
	 * @param string
	 * @param amount
	 * @return
	 */
	WalletTransactionDTO createTransaction(UserDTO userDTO, WALLET_TRANS_TYPE withdrawal, Object object, String msg,
			BigDecimal amount);

}
