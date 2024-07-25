/**
 * @author arif.shaikh 16-Jun-2024
 */
package com.practice.service;

import com.practice.dto.AuthResponse;
import com.practice.dto.UserDTO;

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

}
