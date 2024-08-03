/**
 * @author arif.shaikh 03-Aug-2024
 */
package com.practice.dto;

import java.math.BigDecimal;

import lombok.Data;

/**
 * 
 */

@Data
public class WalletDTO {

	private Long id;

	private UserDTO userDTO;

	private BigDecimal balance;

}
