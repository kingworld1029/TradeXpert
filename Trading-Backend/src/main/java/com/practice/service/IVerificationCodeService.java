/**
 * @author arif.shaikh 01-Aug-2024
 */
package com.practice.service;

import com.practice.dto.UserDTO;
import com.practice.dto.VerificationCodeDTO;
import com.practice.helper.HelperEnum.VERIFICATION_TYPE;

/**
 * 
 */
public interface IVerificationCodeService {

	VerificationCodeDTO sendveificationCode(UserDTO userDTO,VERIFICATION_TYPE verificationType);
	
	VerificationCodeDTO getVerificationCodeById(Long id) throws Exception;
	
	VerificationCodeDTO getVerificationCodeByUser(Long userId) throws Exception;
	
	void deleteVerificationCode(VerificationCodeDTO verificationCodeDTO);
}
