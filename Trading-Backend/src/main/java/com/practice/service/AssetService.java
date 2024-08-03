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
import com.practice.repository.AssetRepository;
import com.practice.utils.ConverterUtility;

/**
 * 
 */
@Service
public class AssetService implements IAssetService {

	@Autowired
	private AssetRepository assetRepository;

	@Override
	public AssetDTO createAsset(UserDTO userDTO, CoinDTO coinDTO, double quantity) {
		AssetEntity assetEntity = new AssetEntity();
		assetEntity.setUserEntity(ConverterUtility.convertUserDTOToEntity(userDTO));
		assetEntity.setCoinEntity(ConverterUtility.convertCoinDTOToEntity(coinDTO));
		assetEntity.setQuantity(quantity);
		assetEntity.setBuyPrice(coinDTO.getCurrentPrice());
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
	public List<AssetDTO> getUserAssets(Long userId) {
		List<AssetEntity> assetEntityList = assetRepository.findByUserId(userId);
		List<AssetDTO> assetDTOList = assetEntityList.stream().map(ConverterUtility::convertAssetEntityToDTO)
				.collect(Collectors.toList());
		return assetDTOList;
	}

	@Override
	public AssetDTO updateAssest(Long assetId, double quantity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AssetDTO findAssetByUserIdAndCoinId(Long userId, String coinId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAsset(Long assetId) {
		// TODO Auto-generated method stub

	}

}
