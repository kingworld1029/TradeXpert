/**
 * @author arif.shaikh 16-Jun-2024
 */
package com.practice.service;

import com.practice.dto.AuthResponse;
import com.practice.dto.UserDTO;
import com.practice.helper.HelperEnum.VERIFICATION_TYPE;

/**
 * 
 */
public interface IUserService {

	/**
	 * @param userDTO
	 * @return
	 * @throws Exception 
	 */
	AuthResponse register(UserDTO userDTO) throws Exception;

	/**
	 * @param userDTO
	 * @return
	 */
	AuthResponse login(UserDTO userDTO);

	/**
	 * @param otp
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	AuthResponse verifySigningOtp(String otp, String id) throws Exception;

	/**
	 * @param jwt
	 * @return
	 * @throws Exception 
	 */
	UserDTO findUserProfileByJwt(String jwt) throws Exception;
	
	UserDTO findUserByEmail(String email) throws Exception;
	
	UserDTO findUserById(Long userId) throws Exception;
	
	UserDTO enableTwoFactorAuthentocation(VERIFICATION_TYPE verificationType, String SendTo, UserDTO userDTO);
	
	UserDTO updatepassword(UserDTO userDTO, String newPassword);

}
