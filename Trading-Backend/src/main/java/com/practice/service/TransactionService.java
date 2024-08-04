/**
 * @author arif.shaikh 05-Aug-2024
 */
package com.practice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.practice.dto.WalletDTO;
import com.practice.dto.WalletTransactionDTO;

/**
 * 
 */
@Service
public class TransactionService implements ITransactionService {

	@Override
	public List<WalletTransactionDTO> getTransactionByWallet(WalletDTO walletDTO) {
		// TODO Auto-generated method stub
		return null;
	}

}
