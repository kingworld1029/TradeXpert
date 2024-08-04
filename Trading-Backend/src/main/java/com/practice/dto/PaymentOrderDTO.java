/**
 * @author arif.shaikh 04-Aug-2024
 */
package com.practice.dto;

import java.math.BigDecimal;

import com.practice.helper.HelperEnum.PAYMENT_METHOD;
import com.practice.helper.HelperEnum.PAYMENT_ORDER_STATUS;

import lombok.Data;

/**
 * 
 */
@Data
public class PaymentOrderDTO {

	private Long id;

	private BigDecimal amount;

	private PAYMENT_ORDER_STATUS paymentStatus;

	private PAYMENT_METHOD paymentMethod;

	private UserDTO userDTO;

}
