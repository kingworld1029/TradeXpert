/**
 * @author arif.shaikh 04-Aug-2024
 */
package com.practice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practice.entity.WithdrawalEntity;

/**
 * 
 */
public interface WithdrawalRepository extends JpaRepository<WithdrawalEntity, Long> {

	List<WithdrawalEntity> findByUserId(Long userid);

}
