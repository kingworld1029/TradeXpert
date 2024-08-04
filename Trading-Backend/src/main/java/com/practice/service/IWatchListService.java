/**
 * @author arif.shaikh 04-Aug-2024
 */
package com.practice.service;

import com.practice.dto.CoinDTO;
import com.practice.dto.UserDTO;
import com.practice.dto.WatchListDTO;

/**
 * 
 */
public interface IWatchListService {

	WatchListDTO findUserWatchList(Long userId) throws Exception;

	WatchListDTO createWatchlist(UserDTO userDTO);

	WatchListDTO findById(Long id);

	CoinDTO addItemToWatchList(CoinDTO coinDTO, UserDTO userDTO);

}
