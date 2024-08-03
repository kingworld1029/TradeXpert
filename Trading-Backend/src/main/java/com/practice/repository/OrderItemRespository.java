/**
 * @author arif.shaikh 03-Aug-2024
 */
package com.practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practice.entity.OrderItemEntity;

/**
 * 
 */
public interface OrderItemRespository extends JpaRepository<OrderItemEntity, Long> {

}
