/**
 * @author arif.shaikh 04-Aug-2024
 */
package com.practice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practice.dto.AssetDTO;
import com.practice.dto.CoinDTO;
import com.practice.dto.UserDTO;
import com.practice.entity.AssetEntity;
import com.practice.entity.CoinEntity;
import com.practice.entity.UserEntity;
import com.practice.repository.AssetRepository;
import com.practice.utils.ConverterUtility;

/**
 * 
 */
@Service
public class AssetService implements IAssetService {

	@Autowired
	private AssetRepository assetRepository;

	@Autowired
	private IUserService userService;

	@Autowired
	private ICoinService coinService;

	@Override
	public AssetDTO createAsset(UserEntity userEntity, CoinEntity coinEntity, double quantity) {
		AssetEntity assetEntity = new AssetEntity();
		assetEntity.setUserEntity(userEntity);
		assetEntity.setCoinEntity(coinEntity);
		assetEntity.setQuantity(quantity);
		assetEntity.setBuyPrice(coinEntity.getCurrentPrice());
		assetRepository.save(assetEntity);
		return ConverterUtility.convertAssetEntityToDTO(assetEntity);
	}

	@Override
	public AssetDTO getAssetById(Long assetId) throws Exception {
		AssetEntity assetEntity = assetRepository.findById(assetId).orElseThrow(() -> new Exception("Asset Not Found"));
		return ConverterUtility.convertAssetEntityToDTO(assetEntity);
	}

	@Override
	public AssetDTO getAssetByUserIdAndId(Long userId, Long assetId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AssetDTO> getUserAssets(Long userId) throws Exception {
		UserEntity userEntity = ConverterUtility.convertUserDTOToEntity(userService.findUserById(userId));
		List<AssetEntity> assetEntityList = assetRepository.findByUserEntity(userEntity);
		List<AssetDTO> assetDTOList = assetEntityList.stream().map(ConverterUtility::convertAssetEntityToDTO)
				.collect(Collectors.toList());
		return assetDTOList;
	}

	@Override
	public AssetDTO updateAssest(Long assetId, double quantity) throws Exception {
		AssetDTO assetDTO = getAssetById(assetId);
		assetDTO.setQuantity(quantity + assetDTO.getQuantity());
		assetRepository.save(ConverterUtility.convertAssetDTOToEntity(assetDTO));
		return assetDTO;
	}

	@Override
	public AssetDTO findAssetByUserIdAndCoinId(Long userId, String coinId) throws Exception {
		UserEntity userEntity = ConverterUtility.convertUserDTOToEntity(userService.findUserById(userId));
		CoinEntity coinEntity = ConverterUtility.convertCoinDTOToEntity(coinService.findById(coinId));
		AssetEntity assetEntity = assetRepository.findByUserEntityAndCoinEntity(userEntity, coinEntity);
		return ConverterUtility.convertAssetEntityToDTO(assetEntity);
	}

	@Override
	public void deleteAsset(Long assetId) {
		assetRepository.deleteById(assetId);

	}

}
