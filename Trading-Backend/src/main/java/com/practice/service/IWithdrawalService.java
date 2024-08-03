/**
 * @author arif.shaikh 04-Aug-2024
 */
package com.practice.service;

import java.math.BigDecimal;
import java.util.List;

import com.practice.dto.UserDTO;
import com.practice.dto.WithdrawalDTO;

/**
 * 
 */
public interface IWithdrawalService {

	WithdrawalDTO requestWithdrawal(BigDecimal amount, UserDTO userDTO);

	WithdrawalDTO proceedWithWithdrawal(Long withdrawalId, boolean accept) throws Exception;

	List<WithdrawalDTO> getUserWithdrawalHistory(UserDTO userDTO);

	List<WithdrawalDTO> getAllWithdrawalRequest();

}
