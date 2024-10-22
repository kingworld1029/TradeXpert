/**
 * @author arif.shaikh 29-Jun-2024
 */
package com.practice.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * 
 */
@Data
@Entity
@Table(name = "TB_TWO_FACTOR_OTP")
public class TwoFactorOTPEntity {
	
	@Id
	private String Id;
	
	private String otp;
	
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@OneToOne
	private UserEntity userEntity;
	
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String jwt;

}
