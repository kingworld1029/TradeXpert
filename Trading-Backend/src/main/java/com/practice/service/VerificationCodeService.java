/**
 * @author arif.shaikh 01-Aug-2024
 */
package com.practice.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practice.dto.UserDTO;
import com.practice.dto.VerificationCodeDTO;
import com.practice.entity.UserEntity;
import com.practice.entity.VerificationCodeEntity;
import com.practice.helper.HelperEnum.VERIFICATION_TYPE;
import com.practice.repository.VerificationCodeRepository;
import com.practice.utils.ConverterUtility;
import com.practice.utils.OtpUtility;

/**
 * 
 */
@Service
public class VerificationCodeService implements IVerificationCodeService {

	@Autowired
	private VerificationCodeRepository verificationCodeRepository;

	@Override
	public VerificationCodeDTO sendveificationCode(UserDTO userDTO, VERIFICATION_TYPE verificationType) {
		VerificationCodeEntity verificationCodeEntity = new VerificationCodeEntity();
		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(userDTO, userEntity);
		verificationCodeEntity.setOtp(OtpUtility.generateOtp());
		verificationCodeEntity.setVerificationType(verificationType);
		verificationCodeEntity.setUserEntity(userEntity);
		verificationCodeEntity = verificationCodeRepository.save(verificationCodeEntity);
		return ConverterUtility.convertCodeEntityToDTO(verificationCodeEntity);
	}

	@Override
	public VerificationCodeDTO getVerificationCodeById(Long id) throws Exception {
		Optional<VerificationCodeEntity> verificationCodeEntity = verificationCodeRepository.findById(id);
		if (verificationCodeEntity.isPresent()) {
			return ConverterUtility.convertCodeEntityToDTO(verificationCodeEntity.get());
		}
		throw new Exception("Verification code Not Found");
	}

	@Override
	public VerificationCodeDTO getVerificationCodeByUser(Long userId) {
		VerificationCodeEntity verificationCodeEntity = verificationCodeRepository.findByUserId(userId);
		return ConverterUtility.convertCodeEntityToDTO(verificationCodeEntity);
	}

	@Override
	public void deleteVerificationCode(VerificationCodeDTO verificationCodeDTO) {
		VerificationCodeEntity verificationCodeEntity = new VerificationCodeEntity();
		BeanUtils.copyProperties(verificationCodeDTO, verificationCodeEntity);
		verificationCodeRepository.delete(verificationCodeEntity);

	}

}
