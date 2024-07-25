/**
 * @author arif.shaikh 16-Jun-2024
 */
package com.practice.entity;

import com.practice.helper.HelperEnum.VERIFICATION_TYPE;

import lombok.Data;

/**
 * 
 */
@Data
public class TwoFactorAuthEntity {
	
	private boolean isEnabled =false;
	
	private VERIFICATION_TYPE sendTO;

}
