/**
 * @author arif.shaikh 29-Jun-2024
 */
package com.practice.service;

import com.practice.dto.TwoFactorOTPDTO;
import com.practice.dto.UserDTO;

/**
 * 
 */
public interface ITwoFactorOTPService {

	TwoFactorOTPDTO createTwoFactorOtp(UserDTO user, String otp, String jwt );
	
	TwoFactorOTPDTO findByUser(Long userId);
	
	TwoFactorOTPDTO findById(String id);
	
	boolean verifyTwoFactorOtp(TwoFactorOTPDTO twoFactorOTPDTO, String otp);
	
	void deleteTwoFactorOtp(TwoFactorOTPDTO twoFactorOTPDTO);
}
