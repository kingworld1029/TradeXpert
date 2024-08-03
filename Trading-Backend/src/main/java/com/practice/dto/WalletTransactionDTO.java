/**
 * @author arif.shaikh 03-Aug-2024
 */
package com.practice.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.practice.helper.HelperEnum.WALLET_TRANS_TYPE;

import lombok.Data;

/**
 * 
 */
@Data
public class WalletTransactionDTO {
	private Long id;

	private WalletDTO walletDTO;

	private WALLET_TRANS_TYPE walletTransType;

	private LocalDateTime date;

	private String transferId;

	private String purpose;

	private BigDecimal amount;
}
