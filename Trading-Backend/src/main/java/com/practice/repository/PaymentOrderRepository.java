/**
 * @author arif.shaikh 04-Aug-2024
 */
package com.practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practice.entity.PaymentOrderEntity;

/**
 * 
 */
public interface PaymentOrderRepository extends JpaRepository<PaymentOrderEntity, Long> {

}
