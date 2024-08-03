/**
 * @author arif.shaikh 03-Aug-2024
 */
package com.practice.service;

import java.math.BigDecimal;

import com.practice.dto.OrderDTO;
import com.practice.dto.UserDTO;
import com.practice.dto.WalletDTO;

/**
 * 
 */
public interface IWalletService {

	WalletDTO getUserWallet(UserDTO userDTO);

	WalletDTO addBalance(WalletDTO walletDTO, BigDecimal money);

	WalletDTO findById(Long id) throws Exception;

	WalletDTO walletToWalletTransfer(UserDTO sender, WalletDTO receiverWalletDTO, BigDecimal amount) throws Exception;

	WalletDTO payOrderpayment(OrderDTO orderDTO, UserDTO userDTO) throws Exception;
}
