/**
 * @author arif.shaikh 03-Aug-2024
 */
package com.practice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practice.entity.OrderEntity;

/**
 * 
 */
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

	List<OrderEntity> findByuserId(Long userId);

}
