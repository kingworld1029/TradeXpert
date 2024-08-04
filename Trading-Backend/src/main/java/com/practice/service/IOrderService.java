/**
 * @author arif.shaikh 03-Aug-2024
 */
package com.practice.service;

import java.util.List;

import com.practice.dto.CoinDTO;
import com.practice.dto.OrderDTO;
import com.practice.dto.OrderItemDTO;
import com.practice.dto.UserDTO;
import com.practice.entity.OrderEntity;
import com.practice.entity.OrderItemEntity;
import com.practice.entity.UserEntity;
import com.practice.helper.HelperEnum.ORDER_TYPE;

/**
 * 
 */
public interface IOrderService {

	OrderEntity createOrder(UserEntity userEntity, OrderItemEntity orderItemEntity, ORDER_TYPE orderType);

	OrderDTO getOrderById(Long orderId) throws Exception;

	List<OrderDTO> getAllOrdersOfUsers(Long userId, ORDER_TYPE orderType, String assetSymbo) throws Exception;

	OrderDTO processOrder(CoinDTO coinDTO, double quantity, ORDER_TYPE orderType, UserDTO userDTO) throws Exception;
}
