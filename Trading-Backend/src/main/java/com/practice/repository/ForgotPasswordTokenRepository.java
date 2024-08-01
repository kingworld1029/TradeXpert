/**
 * @author arif.shaikh 01-Aug-2024
 */
package com.practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practice.entity.ForgotPasswordTokenEntity;

/**
 * 
 */
public interface ForgotPasswordTokenRepository extends JpaRepository<ForgotPasswordTokenEntity, String> {
	
	ForgotPasswordTokenEntity findByUserId(Long userId);

}
