/**
 * @author arif.shaikh 16-Jun-2024
 */
package com.practice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.practice.helper.HelperEnum.USER_ROLE;

import lombok.Data;

/**
 * 
 */
@Data
public class UserDTO {

	private Long Id;
	
	private String fullName;
	
	private String email;
	
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;
	
	private String mobileNo;

	private TwoFactorAuthDTO twoFactorAuthDTO= new TwoFactorAuthDTO();
	
	private USER_ROLE role=USER_ROLE.ROLE_CUSTOMER;
}
