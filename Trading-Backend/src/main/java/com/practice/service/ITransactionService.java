/**
 * @author arif.shaikh 05-Aug-2024
 */
package com.practice.service;

import java.util.List;

import com.practice.dto.WalletDTO;
import com.practice.dto.WalletTransactionDTO;

/**
 * 
 */
public interface ITransactionService {

	/**
	 * @param walletDTO
	 * @return
	 */
	List<WalletTransactionDTO> getTransactionByWallet(WalletDTO walletDTO);

}
