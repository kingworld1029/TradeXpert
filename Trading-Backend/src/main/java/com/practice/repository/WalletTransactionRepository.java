/**
 * @author arif.shaikh 06-Aug-2024
 */
package com.practice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practice.entity.WalletEntity;
import com.practice.entity.WalletTransactionEntity;

/**
 * 
 */
public interface WalletTransactionRepository extends JpaRepository<WalletTransactionEntity, Long> {

	/**
	 * @param convertWalletDTOToEntity
	 * @return
	 */
	List<WalletTransactionEntity> findByWalletEntity(WalletEntity convertWalletEntity);

}
