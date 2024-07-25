/**
 * @author arif.shaikh 16-Jun-2024
 */
package com.practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.practice.entity.UserEntity;

/**
 * 
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

	/**
	 * @param email
	 * @return
	 */
	UserEntity findByEmail(String email);

}
