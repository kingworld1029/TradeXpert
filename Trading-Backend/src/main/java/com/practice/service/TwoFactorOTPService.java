/**
 * @author arif.shaikh 29-Jun-2024
 */
package com.practice.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practice.dto.TwoFactorOTPDTO;
import com.practice.dto.UserDTO;
import com.practice.entity.TwoFactorOTPEntity;
import com.practice.entity.UserEntity;
import com.practice.repository.TwoFactorOTPRepository;

/**
 * 
 */
@Service
public class TwoFactorOTPService implements ITwoFactorOTPService {
	
	@Autowired
	private TwoFactorOTPRepository twoFactorOTPRepository;

	@Override
	public TwoFactorOTPDTO createTwoFactorOtp(UserDTO user, String otp, String jwt) {
		UUID uuid = UUID.randomUUID();
		String id = uuid.toString();
		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(user, userEntity);
		TwoFactorOTPEntity twoFactorOTPEntity  = new TwoFactorOTPEntity();
		twoFactorOTPEntity.setOtp(otp);
		twoFactorOTPEntity.setJwt(jwt);
		twoFactorOTPEntity.setUser(userEntity);
		twoFactorOTPEntity.setId(id);
		twoFactorOTPEntity = twoFactorOTPRepository.save(twoFactorOTPEntity);
		return covertEntityToDTO(twoFactorOTPEntity);
	}

	@Override
	public TwoFactorOTPDTO findByUser(Long userId) {
		TwoFactorOTPEntity twoFactorOTPEntity=twoFactorOTPRepository.findByUserId(userId);
		return covertEntityToDTO(twoFactorOTPEntity);
	}

	@Override
	public TwoFactorOTPDTO findById(String id) {
		Optional<TwoFactorOTPEntity> twoFactorOTPEntity  = twoFactorOTPRepository.findById(id);
		if(twoFactorOTPEntity.isPresent()) {
			return covertEntityToDTO(twoFactorOTPEntity.get());
		}
		return null;
	}

	@Override
	public boolean verifyTwoFactorOtp(TwoFactorOTPDTO twoFactorOTPDTO, String otp) {
		return twoFactorOTPDTO.getOtp().equals(otp);
	}

	@Override
	public void deleteTwoFactorOtp(TwoFactorOTPDTO twoFactorOTPDTO) {
		twoFactorOTPRepository.deleteById(twoFactorOTPDTO.getId());
		
	}
	
	public TwoFactorOTPDTO covertEntityToDTO(TwoFactorOTPEntity twoFactorOTPEntity){
		TwoFactorOTPDTO twoFactorOTPDTO =new TwoFactorOTPDTO();
		BeanUtils.copyProperties(twoFactorOTPEntity, twoFactorOTPDTO);
		return twoFactorOTPDTO;
	}

	
}
