/**
 * @author arif.shaikh 03-Aug-2024
 */
package com.practice.dto;

import com.practice.helper.HelperEnum.ORDER_TYPE;

import lombok.Data;

/**
 * 
 */
@Data
public class CreateOrderRequestDTO {
	private String coinId;
	private double quantity;
	private ORDER_TYPE orderType;

}
