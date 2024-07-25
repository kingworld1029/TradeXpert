/**
 * @author arif.shaikh 29-Jun-2024
 */
package com.practice.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String Id;
	
	private String otp;
	
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@OneToOne
	private UserEntity user;
	
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String jwt;

}
