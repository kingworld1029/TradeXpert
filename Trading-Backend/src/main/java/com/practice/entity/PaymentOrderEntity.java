/**
 * @author arif.shaikh 04-Aug-2024
 */
package com.practice.entity;

import java.math.BigDecimal;

import com.practice.helper.HelperEnum.PAYMENT_METHOD;
import com.practice.helper.HelperEnum.PAYMENT_ORDER_STATUS;

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
@Table(name = "TB_PAYMENT_ORDER")
public class PaymentOrderEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private BigDecimal amount;

	private PAYMENT_ORDER_STATUS paymentStatus;

	private PAYMENT_METHOD paymentMethod;

	@ManyToOne
	private UserEntity userEntity;
}
