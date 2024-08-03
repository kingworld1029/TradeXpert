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
public class VerificationCodeDTO {
	private Long id;

	private String otp;

	private UserDTO userDTO;

	private String email;

	private String mobile;

	private VERIFICATION_TYPE verificationType;
}
