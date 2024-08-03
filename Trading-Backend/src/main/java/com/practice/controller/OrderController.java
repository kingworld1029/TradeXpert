/**
 * @author arif.shaikh 03-Aug-2024
 */
package com.practice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.practice.dto.CoinDTO;
import com.practice.dto.CreateOrderRequestDTO;
import com.practice.dto.OrderDTO;
import com.practice.dto.UserDTO;
import com.practice.helper.HelperEnum.ORDER_TYPE;
import com.practice.service.ICoinService;
import com.practice.service.IOrderService;
import com.practice.service.IUserService;
import com.practice.service.IWalletTransactionService;

/**
 * 
 */
@RestController
@RequestMapping("/api/orders")
public class OrderController {

	@Autowired
	private IOrderService orderService;

	@Autowired
	private IUserService userService;

	@Autowired
	private ICoinService coinService;

	@Autowired
	private IWalletTransactionService walletTransactionService;

	@PostMapping("/pay")
	public ResponseEntity<OrderDTO> payOrderPayment(@RequestHeader("Authorization") String jwt,
			@RequestBody CreateOrderRequestDTO createOrderRequestDTO) throws Exception {

		UserDTO userDTO = userService.findUserProfileByJwt(jwt);
		CoinDTO coinDTO = coinService.findById(createOrderRequestDTO.getCoinId());
		OrderDTO orderDTO = orderService.processOrder(coinDTO, createOrderRequestDTO.getQuantity(),
				createOrderRequestDTO.getOrderType(), userDTO);
		return ResponseEntity.ok(orderDTO);

	}

	@GetMapping("/{orderId}")
	public ResponseEntity<OrderDTO> getOrderById(@RequestHeader("Authorization") String jwt, Long orderId)
			throws Exception {
		UserDTO userDTO = userService.findUserProfileByJwt(jwt);
		OrderDTO orderDTO = orderService.getOrderById(orderId);
		if (orderDTO.getUserDTO().getId().equals(userDTO.getId())) {
			return ResponseEntity.ok(orderDTO);
		} else {
			throw new Exception("You dont Have Access");
		}

	}

	@GetMapping("/")
	public ResponseEntity<List<OrderDTO>> getAllOrder(@RequestHeader("Authorization") String jwt,
			@RequestParam(value = "order_type", required = false) ORDER_TYPE orderType,
			@RequestParam(value = "asset_symbol", required = false) String assetSymbol) throws Exception {
		Long userId = userService.findUserProfileByJwt(jwt).getId();
		List<OrderDTO> orderDTOList = orderService.getAllOrdersOfUsers(userId, orderType, assetSymbol);
		return ResponseEntity.ok(orderDTOList);

	}

}
