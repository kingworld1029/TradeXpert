/**
 * @author arif.shaikh 04-Aug-2024
 */
package com.practice.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practice.dto.UserDTO;
import com.practice.dto.WithdrawalDTO;
import com.practice.entity.UserEntity;
import com.practice.entity.WithdrawalEntity;
import com.practice.helper.HelperEnum.WITHDRAWAL_STATUS;
import com.practice.repository.WithdrawalRepository;
import com.practice.utils.ConverterUtility;

/**
 * 
 */
@Service
public class WithdrawalService implements IWithdrawalService {

	@Autowired
	private WithdrawalRepository withdrawalRepository;

	@Override
	public WithdrawalDTO requestWithdrawal(BigDecimal amount, UserDTO userDTO) {
		WithdrawalEntity withdrawalEntity = new WithdrawalEntity();
		withdrawalEntity.setAmount(amount);
		withdrawalEntity.setUserEntity(ConverterUtility.convertUserDTOToEntity(userDTO));
		withdrawalEntity.setStatus(WITHDRAWAL_STATUS.PENDING);
		withdrawalRepository.save(withdrawalEntity);
		return ConverterUtility.convertWithdrawalEntityToDTO(withdrawalEntity);
	}

	@Override
	public WithdrawalDTO proceedWithWithdrawal(Long withdrawalId, boolean accept) throws Exception {
		Optional<WithdrawalEntity> withdrawalEntity = withdrawalRepository.findById(withdrawalId);
		if (withdrawalEntity.isEmpty()) {
			throw new Exception("WithDrawala Not Found");
		}
		WithdrawalEntity withdrawalEntity1 = withdrawalEntity.get();
		withdrawalEntity1.setTimeStamp(LocalDateTime.now());
		if (accept) {
			withdrawalEntity1.setStatus(WITHDRAWAL_STATUS.SUCCESS);
		} else {
			withdrawalEntity1.setStatus(WITHDRAWAL_STATUS.PENDING);
		}
		withdrawalRepository.save(withdrawalEntity1);
		return ConverterUtility.convertWithdrawalEntityToDTO(withdrawalEntity1);
	}

	@Override
	public List<WithdrawalDTO> getUserWithdrawalHistory(UserDTO userDTO) {
		UserEntity userEntity = ConverterUtility.convertUserDTOToEntity(userDTO);
		List<WithdrawalEntity> withdrawalEntityList = withdrawalRepository.findByUserEntity(userEntity);
		List<WithdrawalDTO> withdrawalDTOList = withdrawalEntityList.stream()
				.map(ConverterUtility::convertWithdrawalEntityToDTO).collect(Collectors.toList());
		return withdrawalDTOList;
	}

	@Override
	public List<WithdrawalDTO> getAllWithdrawalRequest() {
		List<WithdrawalEntity> withdrawalEntityList = withdrawalRepository.findAll();
		List<WithdrawalDTO> withdrawalDTOList = withdrawalEntityList.stream()
				.map(ConverterUtility::convertWithdrawalEntityToDTO).collect(Collectors.toList());
		return withdrawalDTOList;
	}

}
