/**
 * @author arif.shaikh 04-Aug-2024
 */
package com.practice.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * 
 */
@Data
public class WatchListDTO {

	private Long id;

	private UserDTO userDTO;

	private List<CoinDTO> coinDTOs = new ArrayList<>();

}
