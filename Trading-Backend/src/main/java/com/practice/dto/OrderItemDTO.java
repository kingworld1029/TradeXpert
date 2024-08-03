/**
 * @author arif.shaikh 03-Aug-2024
 */
package com.practice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * 
 */
@Data
public class OrderItemDTO {

	private Long id;

	private double quantity;

	private CoinDTO coinDTO;

	private double buyPrice;

	private double sellPrice;

	@JsonIgnore
	private OrderDTO orderDTO;
}
