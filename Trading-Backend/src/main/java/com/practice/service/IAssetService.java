/**
 * @author arif.shaikh 04-Aug-2024
 */
package com.practice.service;

import java.util.List;

import com.practice.dto.AssetDTO;
import com.practice.dto.CoinDTO;
import com.practice.dto.UserDTO;
import com.practice.entity.CoinEntity;
import com.practice.entity.UserEntity;

/**
 * 
 */
public interface IAssetService {

	AssetDTO createAsset(UserEntity userEntity, CoinEntity coinEntity, double quantity);

	AssetDTO getAssetById(Long assetId) throws Exception;

	AssetDTO getAssetByUserIdAndId(Long userId, Long assetId);

	List<AssetDTO> getUserAssets(Long userId) throws Exception;

	AssetDTO updateAssest(Long assetId, double quantity) throws Exception;

	AssetDTO findAssetByUserIdAndCoinId(Long userId, String coinId)throws Exception;

	void deleteAsset(Long assetId);

}
