/**
 * @author arif.shaikh 04-Aug-2024
 */
package com.practice.service;

import java.util.List;

import com.practice.dto.AssetDTO;
import com.practice.dto.CoinDTO;
import com.practice.dto.UserDTO;

/**
 * 
 */
public interface IAssetService {

	AssetDTO createAsset(UserDTO userDTO, CoinDTO coinDTO, double quantity);

	AssetDTO getAssetById(Long assetId) throws Exception;

	AssetDTO getAssetByUserIdAndId(Long userId, Long assetId);

	List<AssetDTO> getUserAssets(Long userId);

	AssetDTO updateAssest(Long assetId, double quantity);

	AssetDTO findAssetByUserIdAndCoinId(Long userId, String coinId);

	void deleteAsset(Long assetId);

}
