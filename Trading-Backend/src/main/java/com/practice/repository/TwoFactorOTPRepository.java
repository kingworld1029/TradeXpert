/**
 * @author arif.shaikh 29-Jun-2024
 */
package com.practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practice.entity.TwoFactorOTPEntity;
import com.practice.entity.UserEntity;

/**
 * 
 */
public interface TwoFactorOTPRepository extends JpaRepository<TwoFactorOTPEntity,String>{
	
	TwoFactorOTPEntity findByUser(UserEntity userEntity);

}
