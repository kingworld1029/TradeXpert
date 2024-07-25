/**
 * @author arif.shaikh 16-Jun-2024
 */
package com.practice.dto;

import com.practice.helper.HelperEnum.VERIFICATION_TYPE;

import lombok.Data;

/**
 * 
 */
@Data
public class TwoFactorAuthDTO {
	private boolean isEnabled = false;

	private VERIFICATION_TYPE sendTO;
}
