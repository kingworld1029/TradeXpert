/**
 * @author arif.shaikh 03-Aug-2024
 */
package com.practice.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.practice.entity.OrderItemEntity;
import com.practice.entity.UserEntity;
import com.practice.helper.HelperEnum.ORDER_STATUS;
import com.practice.helper.HelperEnum.ORDER_TYPE;

import lombok.Data;

/**
 * 
 */
@Data
public class OrderDTO {

	private Long Id;

	private UserDTO userDTO;

	private ORDER_TYPE orderType;

	private BigDecimal price;

	private LocalDateTime timestamp = LocalDateTime.now();

	private ORDER_STATUS orderStatus;

	private OrderItemDTO orderItemDTO;
}
