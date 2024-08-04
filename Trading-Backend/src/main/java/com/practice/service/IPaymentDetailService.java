/**
 * @author arif.shaikh 04-Aug-2024
 */
package com.practice.service;

import com.practice.dto.PaymentDetailDTO;
import com.practice.dto.UserDTO;

/**
 * 
 */
public interface IPaymentDetailService {

	PaymentDetailDTO addPaymnetDetail(String accountNumber, String accountHolderName, String ifsc, String bankName,
			UserDTO userDTO);

	PaymentDetailDTO getUserPaymentDetail(UserDTO userDTO);
}
