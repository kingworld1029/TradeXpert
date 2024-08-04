/**
 * @author arif.shaikh 04-Aug-2024
 */
package com.practice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.dto.CoinDTO;
import com.practice.dto.UserDTO;
import com.practice.dto.WatchListDTO;
import com.practice.service.ICoinService;
import com.practice.service.IUserService;
import com.practice.service.IWatchListService;

/**
 * 
 */
@RestController
@RequestMapping("/api/watchList")
public class WatchListController {

	@Autowired
	private IWatchListService watchListService;

	@Autowired
	private IUserService userService;

	@Autowired
	private ICoinService coinService;

	@GetMapping("/user")
	public ResponseEntity<WatchListDTO> getUserWacthList(@RequestHeader("Authorization") String jwt) throws Exception {
		UserDTO userDTO = userService.findUserProfileByJwt(jwt);
		WatchListDTO watchListDTO = watchListService.findUserWatchList(userDTO);
		return ResponseEntity.ok(watchListDTO);

	}

	@GetMapping("/{watchListId}")
	public ResponseEntity<WatchListDTO> getWatchListById(@PathVariable Long watchListId) throws Exception {
		WatchListDTO watchListDTO = watchListService.findById(watchListId);
		return ResponseEntity.ok(watchListDTO);

	}

	@PatchMapping("/addItem/{coinId}")
	public ResponseEntity<CoinDTO> addItemToWatchList(@RequestHeader("Authorization") String jwt,
			@PathVariable String coinId) throws Exception {
		UserDTO userDTO = userService.findUserProfileByJwt(jwt);
		CoinDTO coinDTO = coinService.findById(coinId);
		CoinDTO addedCoinDTO = watchListService.addItemToWatchList(coinDTO, userDTO);
		return ResponseEntity.ok(addedCoinDTO);

	}

}
