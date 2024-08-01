/**
 * @author arif.shaikh 01-Aug-2024
 */
package com.practice.service;

import com.practice.dto.ForgotPasswordTokenDTO;
import com.practice.dto.UserDTO;
import com.practice.helper.HelperEnum.VERIFICATION_TYPE;

/**
 * 
 */
public interface IForgotPasswordService {

	ForgotPasswordTokenDTO createToken(UserDTO userDTO, String id, String otp, VERIFICATION_TYPE verificationType, String sendTo);
	
	ForgotPasswordTokenDTO findById(String id);
	
	ForgotPasswordTokenDTO findByUser(Long userId);
	
	void deleteToken(ForgotPasswordTokenDTO forgotPasswordTokenDTO);
}
