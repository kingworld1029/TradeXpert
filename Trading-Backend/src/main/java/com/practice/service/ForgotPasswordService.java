/**
 * @author arif.shaikh 01-Aug-2024
 */
package com.practice.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practice.dto.ForgotPasswordTokenDTO;
import com.practice.dto.UserDTO;
import com.practice.entity.ForgotPasswordTokenEntity;
import com.practice.entity.UserEntity;
import com.practice.helper.HelperEnum.VERIFICATION_TYPE;
import com.practice.repository.ForgotPasswordTokenRepository;

/**
 * 
 */
@Service
public class ForgotPasswordService implements IForgotPasswordService {

	@Autowired
	private ForgotPasswordTokenRepository forgotPasswordTokenRepository;

	@Override
	public ForgotPasswordTokenDTO createToken(UserDTO userDTO, String id, String otp,
			VERIFICATION_TYPE verificationType, String sendTo) {
		UserEntity userEntity = new UserEntity();
		ForgotPasswordTokenEntity forgotPasswordTokenEntity = new ForgotPasswordTokenEntity();
		BeanUtils.copyProperties(userDTO, userEntity);
		forgotPasswordTokenEntity.setUserEntity(userEntity);
		forgotPasswordTokenEntity.setSendTo(sendTo);
		forgotPasswordTokenEntity.setVerificationType(verificationType);
		forgotPasswordTokenEntity.setOtp(otp);
		forgotPasswordTokenEntity.setId(id);
		forgotPasswordTokenEntity = forgotPasswordTokenRepository.save(forgotPasswordTokenEntity);
		return covertEntityToDTO(forgotPasswordTokenEntity);
	}

	@Override
	public ForgotPasswordTokenDTO findById(String id) {
		Optional<ForgotPasswordTokenEntity> forgotPasswordTokenEntity = forgotPasswordTokenRepository.findById(id);
		if (forgotPasswordTokenEntity.isPresent()) {
			covertEntityToDTO(forgotPasswordTokenEntity.get());
		}
		return null;
	}

	@Override
	public ForgotPasswordTokenDTO findByUser(Long userId) {
		ForgotPasswordTokenEntity forgotPasswordTokenEntity = forgotPasswordTokenRepository.findByUserId(userId);
		return covertEntityToDTO(forgotPasswordTokenEntity);
	}

	@Override
	public void deleteToken(ForgotPasswordTokenDTO forgotPasswordTokenDTO) {
		ForgotPasswordTokenEntity forgotPasswordTokenEntity = new ForgotPasswordTokenEntity();
		BeanUtils.copyProperties(forgotPasswordTokenDTO, forgotPasswordTokenEntity);
		forgotPasswordTokenRepository.delete(forgotPasswordTokenEntity);
	}

	public ForgotPasswordTokenDTO covertEntityToDTO(ForgotPasswordTokenEntity forgotPasswordTokenEntity) {
		ForgotPasswordTokenDTO forgotPasswordTokenDTO = new ForgotPasswordTokenDTO();
		if (forgotPasswordTokenEntity != null) {
			BeanUtils.copyProperties(forgotPasswordTokenEntity, forgotPasswordTokenDTO);
			if (forgotPasswordTokenEntity.getUserEntity() != null) {
				UserDTO userDTO = new UserDTO();
				BeanUtils.copyProperties(forgotPasswordTokenEntity.getUserEntity(), userDTO);
				forgotPasswordTokenDTO.setUserDTO(userDTO);
			}
			return forgotPasswordTokenDTO;
		}
		return null;

	}

}
