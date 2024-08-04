/**
 * @author arif.shaikh 04-Aug-2024
 */
package com.practice.service;

import java.math.BigDecimal;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.practice.dto.PaymentOrderDTO;
import com.practice.dto.UserDTO;
import com.practice.entity.PaymentOrderEntity;
import com.practice.helper.HelperEnum.PAYMENT_METHOD;
import com.practice.helper.HelperEnum.PAYMENT_ORDER_STATUS;
import com.practice.repository.PaymentOrderRepository;
import com.practice.utils.ConverterUtility;
import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

/**
 * 
 */
@Service
public class PaymentOrderService implements IPaymentOrderService {

	@Autowired
	private PaymentOrderRepository paymentOrderRepository;

	@Value("${stripe.secretKey}")
	private String stripeSecretKey;

	@Value("${razorpay.apiKey}")
	private String razorpayApiKey;

	@Value("${razorpay.secretKey}")
	private String razorpaySecretKey;

	@Override
	public PaymentOrderDTO createOrder(UserDTO userDTO, BigDecimal amount, PAYMENT_METHOD paymentMehtod) {
		PaymentOrderEntity paymentOrderEntity = new PaymentOrderEntity();
		paymentOrderEntity.setUserEntity(ConverterUtility.convertUserDTOToEntity(userDTO));
		paymentOrderEntity.setAmount(amount);
		paymentOrderEntity.setPaymentMethod(paymentMehtod);
		paymentOrderEntity = paymentOrderRepository.save(paymentOrderEntity);
		return ConverterUtility.convertPaymentOrderEntityToDTO(paymentOrderEntity);
	}

	@Override
	public PaymentOrderDTO getPaymentOrderById(Long id) throws Exception {
		PaymentOrderEntity paymentOrderEntity = paymentOrderRepository.findById(id)
				.orElseThrow(() -> new Exception("Paymnet order Not Found"));
		return ConverterUtility.convertPaymentOrderEntityToDTO(paymentOrderEntity);
	}

	@Override
	public Boolean proceedPaymentOrder(PaymentOrderDTO paymentOrderDTO, String paymentId) throws RazorpayException {
		if (paymentOrderDTO.getPaymentStatus().equals(PAYMENT_ORDER_STATUS.PENDING)) {
			if (paymentOrderDTO.getPaymentMethod().equals(PAYMENT_METHOD.RAZORPAY)) {
				RazorpayClient razorpayClient = new RazorpayClient(razorpayApiKey, razorpaySecretKey);
				Payment payment = razorpayClient.payments.fetch(paymentId);
				Integer amount = payment.get("amount");
				String status = payment.get("status");
				if (status.equals("captured")) {
					paymentOrderDTO.setPaymentStatus(PAYMENT_ORDER_STATUS.SUCCESS);
					return true;
				}
				paymentOrderDTO.setPaymentStatus(PAYMENT_ORDER_STATUS.FAILED);
				paymentOrderRepository.save(ConverterUtility.convertPaymentOrderDTOToEntity(paymentOrderDTO));
				return false;
			}
			paymentOrderDTO.setPaymentStatus(PAYMENT_ORDER_STATUS.SUCCESS);
			paymentOrderRepository.save(ConverterUtility.convertPaymentOrderDTOToEntity(paymentOrderDTO));
			return true;
		}
		return false;
	}

	@Override
	public String createRazorPaymentLink(UserDTO userDTO, Long amount) throws RazorpayException {
		amount = amount * 100;
		try {
			RazorpayClient razorpayClient = new RazorpayClient(razorpayApiKey, razorpaySecretKey);
			JSONObject paymentLinkRequest = new JSONObject();
			paymentLinkRequest.put("amount", amount);
			paymentLinkRequest.put("currency", "INR");

			JSONObject customer = new JSONObject();
			customer.put("name", userDTO.getFullName());
			customer.put("email", userDTO.getEmail());
			paymentLinkRequest.put("customer", customer);

			JSONObject notify = new JSONObject();
			notify.put("email", true);
			paymentLinkRequest.put("notify", notify);

			paymentLinkRequest.put("reminder_enable", true);

			paymentLinkRequest.put("callback_url", "http://localhost:5173/wallet");
			paymentLinkRequest.put("callback_method", "get");

			PaymentLink paymentLink = razorpayClient.paymentLink.create(paymentLinkRequest);
			String paymnetLinkId = paymentLink.get("id");
			String paymnetLinkUrl = paymentLink.get("short_url");
			return paymnetLinkUrl;

		} catch (Exception e) {
			System.out.println("Error Creating Paymnet Link" + e.getMessage());
			throw new RazorpayException(e.getMessage());
		}
	}

	@Override
	public String createStripePaymentLink(UserDTO userDTO, Long amount, Long orderId) throws StripeException {
		Stripe.apiKey = stripeSecretKey;
		SessionCreateParams params = SessionCreateParams.builder()
				.addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
				.setMode(SessionCreateParams.Mode.PAYMENT)
				.setSuccessUrl("http://localhost:5173/wallet?order_id=" + orderId)
				.setCancelUrl(
						"http://localhost:5173/payment/cancel")
				.addLineItem(
						SessionCreateParams.LineItem.builder().setQuantity(1L)
								.setPriceData(
										SessionCreateParams.LineItem.PriceData.builder().setCurrency("usd")
												.setUnitAmount(amount * 100)
												.setProductData(SessionCreateParams.LineItem.PriceData.ProductData
														.builder().setName("Top up Wallet").build())
												.build())
								.build())
				.build();
		Session session = Session.create(params);
		System.out.println("Session -----" + session);
		return session.getUrl();
	}

}
