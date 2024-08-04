/**
 * @author arif.shaikh 04-Aug-2024
 */
package com.practice.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.practice.dto.PaymentDetailDTO;
import com.practice.dto.UserDTO;
import com.practice.entity.PaymentDetailEntity;
import com.practice.repository.PaymentDetailReposiory;
import com.practice.utils.ConverterUtility;

/**
 * 
 */
public class PaymentDetailService implements IPaymentDetailService {

	@Autowired
	private PaymentDetailReposiory paymentDetailReposiory;

	@Override
	public PaymentDetailDTO addPaymnetDetail(String accountNumber, String accountHolderName, String ifsc,
			String bankName, UserDTO userDTO) {
		PaymentDetailEntity paymentDetailEntity = new PaymentDetailEntity();
		paymentDetailEntity.setAccounNumber(bankName);
		paymentDetailEntity.setAccountHolderName(accountHolderName);
		paymentDetailEntity.setIfsc(ifsc);
		paymentDetailEntity.setBankName(bankName);
		paymentDetailEntity.setUserEntity(ConverterUtility.convertUserDTOToEntity(userDTO));
		paymentDetailEntity = paymentDetailReposiory.save(paymentDetailEntity);
		return ConverterUtility.convertPaymentDetailEntityToDTO(paymentDetailEntity);
	}

	@Override
	public PaymentDetailDTO getUserPaymentDetail(UserDTO userDTO) {
		PaymentDetailEntity paymentDetailEntity = paymentDetailReposiory.findByUserId(userDTO.getId());
		return ConverterUtility.convertPaymentDetailEntityToDTO(paymentDetailEntity);
	}

}
