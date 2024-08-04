/**
 * @author arif.shaikh 04-Aug-2024
 */
package com.practice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practice.entity.AssetEntity;
import com.practice.entity.CoinEntity;
import com.practice.entity.UserEntity;

/**
 * 
 */
public interface AssetRepository extends JpaRepository<AssetEntity, Long> {

	List<AssetEntity> findByUserEntity(UserEntity userEntity);

	AssetEntity findByUserEntityAndCoinEntity(UserEntity userEntity, CoinEntity coinEntity);

}
