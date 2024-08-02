/**
 * @author arif.shaikh 02-Aug-2024
 */
package com.practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practice.entity.CoinEntity;

/**
 * 
 */
public interface CoinRespository extends JpaRepository<CoinEntity, String> {
	
	
}
