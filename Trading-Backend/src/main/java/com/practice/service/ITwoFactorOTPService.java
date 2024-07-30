/**
 * @author arif.shaikh 29-Jun-2024
 */
package com.practice.service;

import com.practice.dto.TwoFactorOTPDTO;
import com.practice.dto.UserDTO;
import com.practice.entity.UserEntity;

/**
 * 
 */
public interface ITwoFactorOTPService {

	TwoFactorOTPDTO createTwoFactorOtp(UserDTO user, String otp, String jwt );
	
	TwoFactorOTPDTO findByUser(UserEntity userEntity);
	
	TwoFactorOTPDTO findById(String id);
	
	boolean verifyTwoFactorOtp(TwoFactorOTPDTO twoFactorOTPDTO, String otp);
	
	void deleteTwoFactorOtp(TwoFactorOTPDTO twoFactorOTPDTO);
}
