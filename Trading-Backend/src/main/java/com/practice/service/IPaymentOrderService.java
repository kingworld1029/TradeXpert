/**
 * @author arif.shaikh 04-Aug-2024
 */
package com.practice.service;

import java.math.BigDecimal;

import com.practice.dto.PaymentOrderDTO;
import com.practice.dto.UserDTO;
import com.practice.helper.HelperEnum.PAYMENT_METHOD;
import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;

/**
 * 
 */
public interface IPaymentOrderService {

	PaymentOrderDTO createOrder(UserDTO userDTO, BigDecimal amount, PAYMENT_METHOD paymentMehtod);

	PaymentOrderDTO getPaymentOrderById(Long id) throws Exception;

	Boolean proceedPaymentOrder(PaymentOrderDTO paymentOrderDTO, String paymentId) throws RazorpayException;

	String createRazorPaymentLink(UserDTO userDTO, Long amount) throws RazorpayException;

	String createStripePaymentLink(UserDTO userDTO, Long amount, Long orderId) throws StripeException;
}
