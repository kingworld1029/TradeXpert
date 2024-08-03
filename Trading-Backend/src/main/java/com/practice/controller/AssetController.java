/**
 * @author arif.shaikh 04-Aug-2024
 */
package com.practice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.dto.AssetDTO;
import com.practice.dto.UserDTO;
import com.practice.service.IAssetService;
import com.practice.service.IUserService;

/**
 * 
 */
@RestController
@RequestMapping("/api/asset")
public class AssetController {

	@Autowired
	private IAssetService assetService;

	@Autowired
	private IUserService userService;

	@GetMapping("/{assetId")
	public ResponseEntity<AssetDTO> getAssetById(@PathVariable Long assetId) throws Exception {
		AssetDTO assetDTO = assetService.getAssetById(assetId);
		return ResponseEntity.ok(assetDTO);
	}

	@GetMapping("/getAssetByUserIdAndCoinId/{coinId}")
	public ResponseEntity<AssetDTO> getAssetByUserIdAndCoinId(@RequestHeader("Authorization") String jwt,
			@PathVariable String coinId) throws Exception {
		UserDTO userDTO = userService.findUserProfileByJwt(jwt);
		AssetDTO assetDTO = assetService.findAssetByUserIdAndCoinId(userDTO.getId(), coinId);
		return ResponseEntity.ok(assetDTO);
	}

	@GetMapping()
	public ResponseEntity<List<AssetDTO>> getAssetForUser(@RequestHeader("Authorization") String jwt) throws Exception {
		UserDTO userDTO = userService.findUserProfileByJwt(jwt);
		List<AssetDTO> assetDTOList = assetService.getUserAssets(userDTO.getId());
		return ResponseEntity.ok(assetDTOList);
	}

}
