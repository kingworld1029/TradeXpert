/**
 * @author arif.shaikh 04-Aug-2024
 */
package com.practice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practice.dto.CoinDTO;
import com.practice.dto.UserDTO;
import com.practice.dto.WatchListDTO;
import com.practice.entity.UserEntity;
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
	public WatchListDTO findUserWatchList(UserDTO userDTO) throws Exception {
		UserEntity userEntity = ConverterUtility.convertUserDTOToEntity(userDTO);
		WatchListEntity watchListEntity = watchListRepository.findByUserEntity(userEntity);
		if (watchListEntity == null) {
			throw new Exception("WatchList Not Found");
		}
		return ConverterUtility.convertWatchListEntityToDTO(watchListEntity);
	}

	@Override
	public WatchListDTO createWatchlist(UserDTO userDTO) {
		WatchListEntity watchListEntity = new WatchListEntity();
		watchListEntity.setUserEntity(ConverterUtility.convertUserDTOToEntity(userDTO));
		watchListEntity = watchListRepository.save(watchListEntity);
		return ConverterUtility.convertWatchListEntityToDTO(watchListEntity);
	}

	@Override
	public WatchListDTO findById(Long id) throws Exception {
		Optional<WatchListEntity> watchListEntity = watchListRepository.findById(id);
		if (watchListEntity.isEmpty()) {
			throw new Exception("WatchList Not found");
		}
		return ConverterUtility.convertWatchListEntityToDTO(watchListEntity.get());
	}

	@Override
	public CoinDTO addItemToWatchList(CoinDTO coinDTO, UserDTO userDTO) throws Exception {
		WatchListDTO watchListDTO = findUserWatchList(userDTO);
		if (watchListDTO.getCoinDTOs().contains(coinDTO)) {
			watchListDTO.getCoinDTOs().remove(coinDTO);
		} else {
			watchListDTO.getCoinDTOs().add(coinDTO);
		}
		watchListRepository.save(ConverterUtility.convertWatchListDTOToEntity(watchListDTO));
		return coinDTO;
	}

}
