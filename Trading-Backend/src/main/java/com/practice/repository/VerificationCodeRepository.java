/**
 * @author arif.shaikh 01-Aug-2024
 */
package com.practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practice.entity.UserEntity;
import com.practice.entity.VerificationCodeEntity;

/**
 * 
 */
public interface VerificationCodeRepository extends JpaRepository<VerificationCodeEntity, Long>{

	public VerificationCodeEntity findByUserEntity(UserEntity userEntity);
}
