/**
 * @author arif.shaikh 04-Aug-2024
 */
package com.practice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practice.entity.AssetEntity;

/**
 * 
 */
public interface AssetRepository extends JpaRepository<AssetEntity, Long> {

	List<AssetEntity> findByUserId(Long userId);

	AssetEntity findByUserIdAndCoinId(Long userid, Long coinId);

}
