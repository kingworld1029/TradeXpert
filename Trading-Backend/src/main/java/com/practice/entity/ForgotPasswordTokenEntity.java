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
@Table(name="TB_FORGOT_PASSWORD")
public class ForgotPasswordTokenEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;
	
	@OneToOne
	private UserEntity userEntity;
	
	private String otp;
	
	private VERIFICATION_TYPE verificationType;
	
	private String SendTo;
	
}
