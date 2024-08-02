/**
 * @author arif.shaikh 02-Aug-2024
 */
package com.practice.service;

import java.util.List;

import com.practice.dto.CoinDTO;

/**
 * 
 */
public interface ICoinService {

	List<CoinDTO> getCoinList(int page) throws Exception;

	String getMarketChart(String coinId, int days) throws Exception;

	String getCoinDetails(String coinId) throws Exception;

	CoinDTO findById(String coinId);

	String searchCoin(String keyword);

	String getTop50CoinByMarketCap();

	String getTradingCoins();
}
