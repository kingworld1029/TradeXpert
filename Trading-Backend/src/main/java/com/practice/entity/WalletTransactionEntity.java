/**
 * @author arif.shaikh 03-Aug-2024
 */
package com.practice.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.practice.helper.HelperEnum.WALLET_TRANS_TYPE;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * 
 */
@Data
@Entity
@Table(name = "TB_WALLET_TRANS")
public class WalletTransactionEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	private WalletEntity walletEntity;

	private WALLET_TRANS_TYPE walletTransType;

	private LocalDateTime date;

	private String transferId;

	private String purpose;

	private BigDecimal amount;

}
