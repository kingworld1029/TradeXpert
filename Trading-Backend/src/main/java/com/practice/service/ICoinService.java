/**
 * @author arif.shaikh 02-Aug-2024
 */
package com.practice.service;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.practice.dto.CoinDTO;

/**
 * 
 */
public interface ICoinService {

	List<CoinDTO> getCoinList(int page) throws Exception;

	JsonNode getMarketChart(String coinId, int days) throws Exception;

	JsonNode getCoinDetails(String coinId) throws Exception;

	CoinDTO findById(String coinId) throws Exception;

	JsonNode searchCoin(String keyword) throws Exception;

	JsonNode getTop50CoinByMarketCap() throws Exception;

	JsonNode getTradingCoin() throws Exception;
}
