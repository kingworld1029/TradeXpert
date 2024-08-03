/**
 * @author arif.shaikh 03-Aug-2024
 */
package com.practice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.practice.dto.CoinDTO;
import com.practice.service.ICoinService;

/**
 * 
 */
@RestController
@RequestMapping("/coins")
public class CoinController {

	@Autowired
	private ICoinService coinService;

	@GetMapping
	ResponseEntity<List<CoinDTO>> getCoinList(@RequestParam("page") int page) throws Exception {
		List<CoinDTO> coinList = coinService.getCoinList(page);
		return new ResponseEntity<>(coinList, HttpStatus.ACCEPTED);

	}

	@GetMapping("/getMarketChart/{coinId}")
	ResponseEntity<JsonNode> getMarketChart(@PathVariable String coinid, @RequestParam("days") int days)
			throws Exception {
		JsonNode jsonNode = coinService.getMarketChart(coinid, days);
		return new ResponseEntity<>(jsonNode, HttpStatus.ACCEPTED);

	}

	@GetMapping("/details/{coinId}")
	ResponseEntity<JsonNode> getCoinDetails(@PathVariable String coinid) throws Exception {
		JsonNode jsonNode = coinService.getCoinDetails(coinid);
		return new ResponseEntity<>(jsonNode, HttpStatus.ACCEPTED);

	}

	@GetMapping("/search")
	ResponseEntity<JsonNode> searchCoin(@RequestParam("keyword") String keyword) throws Exception {
		JsonNode jsonNode = coinService.searchCoin(keyword);
		return ResponseEntity.ok(jsonNode);

	}

	@GetMapping("/top50")
	ResponseEntity<JsonNode> getTop50CoinByMarketCap() throws Exception {
		JsonNode jsonNode = coinService.getTop50CoinByMarketCap();
		return ResponseEntity.ok(jsonNode);

	}

	@GetMapping("/trading")
	ResponseEntity<JsonNode> getTradingCoin() throws Exception {
		JsonNode jsonNode = coinService.getTradingCoin();
		return ResponseEntity.ok(jsonNode);

	}

	@GetMapping("/findById/{coinId}")
	ResponseEntity<CoinDTO> findById(@PathVariable String coinid) throws Exception {
		CoinDTO coinList = coinService.findById(coinid);
		return new ResponseEntity<>(coinList, HttpStatus.ACCEPTED);

	}

}
