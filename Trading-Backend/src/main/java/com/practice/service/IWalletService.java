/**
 * @author arif.shaikh 03-Aug-2024
 */
package com.practice.service;

import java.math.BigDecimal;
import java.util.List;

import com.practice.dto.OrderDTO;
import com.practice.dto.UserDTO;
import com.practice.dto.WalletDTO;
import com.practice.dto.WalletTransactionDTO;
import com.practice.helper.HelperEnum.WALLET_TRANS_TYPE;

/**
 * 
 */
public interface IWalletService {

	WalletDTO getUserWallet(UserDTO userDTO);

	WalletDTO addBalance(WalletDTO walletDTO, BigDecimal money);

	WalletDTO findById(Long id) throws Exception;

	WalletDTO walletToWalletTransfer(UserDTO sender, WalletDTO receiverWalletDTO, BigDecimal amount) throws Exception;

	WalletDTO payOrderpayment(OrderDTO orderDTO, UserDTO userDTO) throws Exception;

	/**
	 * @param userDTO
	 * @param withdrawal
	 * @param object
	 * @param string
	 * @param amount
	 * @return
	 */
	WalletTransactionDTO createTransaction(UserDTO userDTO, WALLET_TRANS_TYPE withdrawal, Object object, String string,
			BigDecimal amount);

	/**
	 * @param walletDTO
	 * @return
	 */
	List<WalletTransactionDTO> getTransactionByWallet(WalletDTO walletDTO);
}
