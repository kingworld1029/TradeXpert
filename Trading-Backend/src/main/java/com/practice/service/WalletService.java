/**
 * @author arif.shaikh 03-Aug-2024
 */
package com.practice.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practice.dto.OrderDTO;
import com.practice.dto.UserDTO;
import com.practice.dto.WalletDTO;
import com.practice.dto.WalletTransactionDTO;
import com.practice.entity.UserEntity;
import com.practice.entity.WalletEntity;
import com.practice.entity.WalletTransactionEntity;
import com.practice.helper.HelperEnum.ORDER_TYPE;
import com.practice.helper.HelperEnum.WALLET_TRANS_TYPE;
import com.practice.repository.WalletRepository;
import com.practice.repository.WalletTransactionRepository;
import com.practice.utils.ConverterUtility;

/**
 * 
 */
@Service
public class WalletService implements IWalletService {

	@Autowired
	private WalletRepository walletRepository;

	@Autowired
	private WalletTransactionRepository walletTransactionRepository;

	@Override
	public WalletDTO getUserWallet(UserDTO userDTO) {
		UserEntity userEntity = ConverterUtility.convertUserDTOToEntity(userDTO);
		WalletEntity walletEntity = walletRepository.findByUserEntity(userEntity);
		if (walletEntity == null) {
			walletEntity = new WalletEntity();
			walletEntity.setUserEntity(ConverterUtility.convertUserDTOToEntity(userDTO));
			walletRepository.save(walletEntity);
		}
		return ConverterUtility.convertWalletEntityToDTO(walletEntity);
	}

	@Override
	public WalletDTO addBalance(WalletDTO walletDTO, BigDecimal money) {
		if (walletDTO.getBalance() == null) {
			walletDTO.setBalance(new BigDecimal(0));
		}
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

	@Override
	public WalletTransactionDTO createTransaction(UserDTO userDTO, WALLET_TRANS_TYPE walletTransType,
			WalletDTO walletDTO, String purpose, BigDecimal amount) {
		WalletTransactionEntity walletTransactionEntity = new WalletTransactionEntity();
		walletTransactionEntity.setWalletEntity(ConverterUtility.convertWalletDTOToEntity(walletDTO));
		walletTransactionEntity.setAmount(amount);
		walletTransactionEntity.setDate(LocalDateTime.now());
		walletTransactionEntity.setPurpose(purpose);
		walletTransactionEntity.setTransferId("");
		walletTransactionEntity.setWalletTransType(walletTransType);
		walletTransactionEntity = walletTransactionRepository.save(walletTransactionEntity);
		return ConverterUtility.convertWalletTransEntityToDTO(walletTransactionEntity);
	}

	@Override
	public List<WalletTransactionDTO> getTransactionByWallet(WalletDTO walletDTO) {
		List<WalletTransactionEntity> walletTransactionEntityList = walletTransactionRepository
				.findByWalletEntity(ConverterUtility.convertWalletDTOToEntity(walletDTO));
		List<WalletTransactionDTO> walletTransactionDTOList = walletTransactionEntityList.stream()
				.map(ConverterUtility::convertWalletTransEntityToDTO).collect(Collectors.toList());
		return walletTransactionDTOList;
	}

}
