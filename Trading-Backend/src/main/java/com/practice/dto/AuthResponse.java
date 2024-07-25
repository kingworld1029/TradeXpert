/**
 * @author arif.shaikh 27-Jun-2024
 */
package com.practice.dto;

import lombok.Data;

/**
 * 
 */
@Data
public class AuthResponse {
	
	private String jwt;
	private boolean status;
	private String message;
	private boolean isTwoFactorAuthEnable;
	private String session;

}
