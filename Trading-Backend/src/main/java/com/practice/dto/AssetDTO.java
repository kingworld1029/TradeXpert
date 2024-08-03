/**
 * @author arif.shaikh 04-Aug-2024
 */
package com.practice.dto;

import lombok.Data;

/**
 * 
 */
@Data
public class AssetDTO {

	private Long id;

	private double quantity;

	private double buyPrice;

	private CoinDTO coinDTO;

	private UserDTO userDTO;
}
