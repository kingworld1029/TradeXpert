/**
 * @author arif.shaikh 03-Aug-2024
 */
package com.practice.service;

import java.util.List;

import com.practice.dto.CoinDTO;
import com.practice.dto.OrderDTO;
import com.practice.dto.OrderItemDTO;
import com.practice.dto.UserDTO;
import com.practice.helper.HelperEnum.ORDER_TYPE;

/**
 * 
 */
public interface IOrderService {

	OrderDTO createOrder(UserDTO userDTO, OrderItemDTO orderItemDTO, ORDER_TYPE orderType);

	OrderDTO getOrderById(Long orderId) throws Exception;

	List<OrderDTO> getAllOrdersOfUsers(Long userId, ORDER_TYPE orderType, String assetSymbo);

	OrderDTO processOrder(CoinDTO coinDTO, double quantity, ORDER_TYPE orderType, UserDTO userDTO) throws Exception;
}