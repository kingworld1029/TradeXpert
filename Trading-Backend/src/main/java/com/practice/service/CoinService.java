/**
 * @author arif.shaikh 02-Aug-2024
 */
package com.practice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.dto.CoinDTO;
import com.practice.entity.CoinEntity;
import com.practice.repository.CoinRespository;

/**
 * 
 */
@Service
public class CoinService implements ICoinService {

	@Autowired
	private CoinRespository coinRespository;

	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public List<CoinDTO> getCoinList(int page) throws Exception {
		String url = "https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&per_page=10&page=" + page;
		RestTemplate restTemplate = new RestTemplate();
		try {
			HttpHeaders headers = new HttpHeaders();
			HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
			List<CoinDTO> coinList = objectMapper.readValue(response.getBody(), new TypeReference<List<CoinDTO>>() {
			});
			return coinList;
		} catch (HttpClientErrorException | HttpServerErrorException e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public JsonNode getMarketChart(String coinId, int days) throws Exception {
		String url = "https://api.coingecko.com/api/v3/coins/" + coinId + "/market_chart?vs_currency=usd&days=" + days;
		return getData(url);
	}

	@Override
	public JsonNode getCoinDetails(String coinId) throws Exception {
		String url = "https://api.coingecko.com/api/v3/coins/" + coinId;
		RestTemplate restTemplate = new RestTemplate();
		try {
			JsonNode jsonNode = getData(url);
			CoinEntity coinEntity = new CoinEntity();
			coinEntity.setId(jsonNode.get("id").asText());
			coinEntity.setName(jsonNode.get("name").asText());
			coinEntity.setSymbol(jsonNode.get("symbol").asText());
			coinEntity.setImage(jsonNode.get("image").get("large").asText());
			JsonNode marketData = jsonNode.get("market_data");
			coinEntity.setCurrentPrice(marketData.get("current_price").get("usd").asDouble());
			coinEntity.setMarketCap(marketData.get("market_cap").get("usd").asLong());
			coinEntity.setMarketCapRank(marketData.get("market_cap_rank").asInt());
			coinEntity.setTotalVolume(marketData.get("total_volume").get("usd").asLong());
			coinEntity.setHigh24h(marketData.get("high_24h").get("usd").asDouble());
			coinEntity.setLow24h(marketData.get("low_24h").get("usd").asDouble());
			coinEntity.setPriceChange24h(marketData.get("price_change_24h").asDouble());
			coinEntity.setPriceChangePercentage24h(marketData.get("price_change_percentage_24h").asDouble());
			coinEntity.setMarketCapChange24h(marketData.get("market_cap_change_24h").asLong());
			coinEntity.setMarketCapChangePercentage24h(marketData.get("market_cap_change_percentage_24h").asLong());
			coinEntity.setTotalSupply(marketData.get("total_supply").asLong());
			coinRespository.save(coinEntity);
			return jsonNode;
		} catch (HttpClientErrorException | HttpServerErrorException e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public CoinDTO findById(String coinId) throws Exception {
		CoinDTO coinDTO = new CoinDTO();
		Optional<CoinEntity> coinEntity = coinRespository.findById(coinId);
		if (coinEntity.isEmpty()) {
			throw new Exception("Coin not Found");
		}
		BeanUtils.copyProperties(coinEntity.get(), coinDTO);
		return coinDTO;
	}

	@Override
	public JsonNode searchCoin(String keyword) throws Exception {
		String url = "https://api.coingecko.com/api/v3/search?query=" + keyword;
		return getData(url);
	}

	@Override
	public JsonNode getTop50CoinByMarketCap() throws Exception {
		String url = "https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&per_page=50&page=1";
		return getData(url);
	}

	@Override
	public JsonNode getTrendingCoin() throws Exception {
		String url = "https://api.coingecko.com/api/v3/search/trending";
		return getData(url);

	}

	JsonNode getData(String url) throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		try {
			HttpHeaders headers = new HttpHeaders();
			HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
			JsonNode jsonNode = objectMapper.readTree(response.getBody());
			return jsonNode;
		} catch (HttpClientErrorException | HttpServerErrorException e) {
			throw new Exception(e.getMessage());
		}

	}

}
