/**
 * @author arif.shaikh 04-Aug-2024
 */
package com.practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practice.entity.PaymentDetailEntity;
import com.practice.entity.UserEntity;

/**
 * 
 */
public interface PaymentDetailReposiory extends JpaRepository<PaymentDetailEntity, Long> {

	PaymentDetailEntity findByUserEntity(UserEntity userEntity);

}
