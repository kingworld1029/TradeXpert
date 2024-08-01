/**
 * @author arif.shaikh 01-Aug-2024
 */
package com.practice.entity;

import com.practice.helper.HelperEnum.VERIFICATION_TYPE;

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
@Table(name = "TB_VERIFICATIONCODE")
public class VerificationCodeEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String otp;
	
	@OneToOne
	private UserEntity user;
	
	private String email;
	
	private String mobile;
	
	private VERIFICATION_TYPE verificationType;

}
