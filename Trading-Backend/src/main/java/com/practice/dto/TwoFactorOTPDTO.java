/**
 * @author arif.shaikh 29-Jun-2024
 */
package com.practice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 
 */
@Data
public class TwoFactorOTPDTO {

	private String id;
	private String otp;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private UserDTO userDTO; // Assume UserDTO contains user details that you don't want to expose

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String jwt;

}
