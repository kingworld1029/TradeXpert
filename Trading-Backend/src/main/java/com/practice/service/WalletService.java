/**
 * @author arif.shaikh 03-Aug-2024
 */
package com.practice.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practice.dto.OrderDTO;
import com.practice.dto.UserDTO;
import com.practice.dto.WalletDTO;
import com.practice.entity.WalletEntity;
import com.practice.helper.HelperEnum.ORDER_TYPE;
import com.practice.repository.WalletRepository;
import com.practice.utils.ConverterUtility;

/**
 * 
 */
@Service
public class WalletService implements IWalletService {

	@Autowired
	private WalletRepository walletRepository;

	@Override
	public WalletDTO getUserWallet(UserDTO userDTO) {
		WalletEntity walletEntity = walletRepository.findByUserId(userDTO.getId());
		if (walletEntity == null) {
			walletEntity = new WalletEntity();
			walletEntity.setUserEntity(ConverterUtility.convertUserDTOToEntity(userDTO));
		}
		return ConverterUtility.convertWalletEntityToDTO(walletEntity);
	}

	@Override
	public WalletDTO addBalance(WalletDTO walletDTO, BigDecimal money) {
		walletDTO.setBalance(walletDTO.getBalance().add(money));
		WalletEntity walletEntity = ConverterUtility.convertWalletDTOToEntity(walletDTO);
		walletEntity = walletRepository.save(walletEntity);
		return ConverterUtility.convertWalletEntityToDTO(walletEntity);
	}

	@Override
	public WalletDTO findById(Long id) throws Exception {
		Optional<WalletEntity> walletEntity = walletRepository.findById(id);
		if (walletEntity.isPresent()) {
			return ConverterUtility.convertWalletEntityToDTO(walletEntity.get());
		}
		throw new Exception("Wallet not found");
	}

	@Override
	public WalletDTO walletToWalletTransfer(UserDTO sender, WalletDTO receiverWalletDTO, BigDecimal amount)
			throws Exception {
		WalletDTO senderWalletDTO = getUserWallet(sender);
		if (senderWalletDTO.getBalance().compareTo(amount) < 0) {
			throw new Exception("InSufficient Balance");
		}
		BigDecimal senderBalance = senderWalletDTO.getBalance().subtract(amount);
		senderWalletDTO.setBalance(senderBalance);
		WalletEntity senderWalletEntity = ConverterUtility.convertWalletDTOToEntity(senderWalletDTO);
		walletRepository.save(senderWalletEntity);
		BigDecimal receiverBalance = receiverWalletDTO.getBalance().add(amount);
		receiverWalletDTO.setBalance(receiverBalance);
		WalletEntity receiverWalletEntity = ConverterUtility.convertWalletDTOToEntity(receiverWalletDTO);
		walletRepository.save(receiverWalletEntity);
		return ConverterUtility.convertWalletEntityToDTO(senderWalletEntity);
	}

	@Override
	public WalletDTO payOrderpayment(OrderDTO orderDTO, UserDTO userDTO) throws Exception {
		WalletDTO walletDTO = getUserWallet(userDTO);
		if (orderDTO.getOrderType().equals(ORDER_TYPE.BUY)) {
			if (walletDTO.getBalance().compareTo(orderDTO.getPrice()) < 0) {
				throw new Exception("InSufficient fund for this transaction");
			}
			BigDecimal newBalance = walletDTO.getBalance().subtract(orderDTO.getPrice());
			walletDTO.setBalance(newBalance);
		} else if (orderDTO.getOrderType().equals(ORDER_TYPE.SELL)) {
			BigDecimal newBalance = walletDTO.getBalance().add(orderDTO.getPrice());
			walletDTO.setBalance(newBalance);
		}
		WalletEntity walletEntity = ConverterUtility.convertWalletDTOToEntity(walletDTO);
		walletRepository.save(walletEntity);
		return ConverterUtility.convertWalletEntityToDTO(walletEntity);
	}

}