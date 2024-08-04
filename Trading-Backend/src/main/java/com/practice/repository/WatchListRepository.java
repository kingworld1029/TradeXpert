/**
 * @author arif.shaikh 04-Aug-2024
 */
package com.practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practice.entity.WatchListEntity;

/**
 * 
 */
public interface WatchListRepository extends JpaRepository<WatchListEntity, Long> {

	WatchListEntity findByUserId(Long userId);
}
