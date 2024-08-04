/**
 * @author arif.shaikh 04-Aug-2024
 */
package com.practice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 
 */
@Data
public class PaymentDetailDTO {

	private Long id;

	private String accounNumber;

	private String accountHolderName;

	private String ifsc;

	private String bankName;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private UserDTO userDTO;

}
