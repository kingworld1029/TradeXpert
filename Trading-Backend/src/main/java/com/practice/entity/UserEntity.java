/**
 * @author arif.shaikh 16-Jun-2024
 */
package com.practice.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.practice.helper.HelperEnum.USER_ROLE;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * 
 */

@Data
@Entity
@Table(name = "TB_USER")
public class UserEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long Id;
	
	private String fullName;
	
	private String email;
	
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;
	
	private String mobileNo;
	
	@Embedded
	private TwoFactorAuthEntity twoFactorAuthEntity= new TwoFactorAuthEntity();
	
	private USER_ROLE role=USER_ROLE.ROLE_CUSTOMER;
	
	

}
