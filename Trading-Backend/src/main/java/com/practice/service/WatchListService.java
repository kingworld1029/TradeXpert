/**
 * @author arif.shaikh 04-Aug-2024
 */
package com.practice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practice.dto.CoinDTO;
import com.practice.dto.UserDTO;
import com.practice.dto.WatchListDTO;
import com.practice.entity.WatchListEntity;
import com.practice.repository.WatchListRepository;
import com.practice.utils.ConverterUtility;

/**
 * 
 */
@Service
public class WatchListService implements IWatchListService {

	@Autowired
	private WatchListRepository watchListRepository;

	@Override
	public WatchListDTO findUserWatchList(Long userId) throws Exception {
		WatchListEntity watchListEntity = watchListRepository.findByUserId(userId);
		if (watchListEntity == null) {
			throw new Exception("WatchList Not Found");
		}
		return ConverterUtility.convertWatchListEntityToDTO(watchListEntity);
	}

	@Override
	public WatchListDTO createWatchlist(UserDTO userDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WatchListDTO findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CoinDTO addItemToWatchList(CoinDTO coinDTO, UserDTO userDTO) {
		// TODO Auto-generated method stub
		return null;
	}

}
