/**
 * @author arif.shaikh 01-Aug-2024
 */
package com.practice.dto;

import com.practice.helper.HelperEnum.VERIFICATION_TYPE;

import jakarta.persistence.OneToOne;
import lombok.Data;

/**
 * 
 */
@Data
public class ForgotPasswordTokenDTO {

	private String id;

	private UserDTO userDTO;

	private String otp;

	private VERIFICATION_TYPE verificationType;

	private String SendTo;
	
	private String password;
}
