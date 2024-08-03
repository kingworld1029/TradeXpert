/**
 * @author arif.shaikh 04-Aug-2024
 */
package com.practice.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.practice.helper.HelperEnum.WITHDRAWAL_STATUS;

import lombok.Data;

/**
 * 
 */
@Data
public class WithdrawalDTO {
	private Long id;

	private WITHDRAWAL_STATUS status;

	private BigDecimal amount;

	private UserDTO userDTO;

	private LocalDateTime timeStamp = LocalDateTime.now();
}
