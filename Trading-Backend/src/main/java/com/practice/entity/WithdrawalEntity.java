/**
 * @author arif.shaikh 04-Aug-2024
 */
package com.practice.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.practice.helper.HelperEnum.WITHDRAWAL_STATUS;

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
@Table(name = "TB_WITHDRAWAL")
public class WithdrawalEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private WITHDRAWAL_STATUS status;

	private BigDecimal amount;

	@ManyToOne
	private UserEntity userEntity;

	private LocalDateTime timeStamp = LocalDateTime.now();
}
