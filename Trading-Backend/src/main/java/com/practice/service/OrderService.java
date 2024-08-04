/**
 * @author arif.shaikh 03-Aug-2024
 */
package com.practice.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practice.dto.AssetDTO;
import com.practice.dto.CoinDTO;
import com.practice.dto.OrderDTO;
import com.practice.dto.OrderItemDTO;
import com.practice.dto.UserDTO;
import com.practice.entity.CoinEntity;
import com.practice.entity.OrderEntity;
import com.practice.entity.OrderItemEntity;
import com.practice.entity.UserEntity;
import com.practice.helper.HelperEnum.ORDER_STATUS;
import com.practice.helper.HelperEnum.ORDER_TYPE;
import com.practice.repository.OrderItemRespository;
import com.practice.repository.OrderRepository;
import com.practice.utils.ConverterUtility;

import jakarta.transaction.Transactional;

/**
 * 
 */
@Service
public class OrderService implements IOrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private IWalletService walletService;

	@Autowired
	private OrderItemRespository orderItemRespository;

	@Autowired
	private IAssetService assetService;

	@Autowired
	private IUserService userService;

	@Override
	public OrderEntity createOrder(UserEntity userEntity, OrderItemEntity orderItemEntity, ORDER_TYPE orderType) {
		double price = orderItemEntity.getCoinEntity().getCurrentPrice() * orderItemEntity.getQuantity();
		OrderEntity orderEntity = new OrderEntity();
		orderEntity.setUserEntity(userEntity);
		orderEntity.setOrderItemEntity(orderItemEntity);
		orderEntity.setOrderType(orderType);
		orderEntity.setPrice(BigDecimal.valueOf(price));
		orderEntity.setTimestamp(LocalDateTime.now());
		orderEntity.setOrderStatus(ORDER_STATUS.PENDING);
		return orderRepository.save(orderEntity);
	}

	@Override
	public OrderDTO getOrderById(Long orderId) throws Exception {
		OrderEntity orderEntity = orderRepository.findById(orderId).orElseThrow(() -> new Exception("Order Not Found"));
		return ConverterUtility.convertOrderEntityToDTO(orderEntity);
	}

	@Override
	public List<OrderDTO> getAllOrdersOfUsers(Long userId, ORDER_TYPE orderType, String assetSymbo) throws Exception {
		UserEntity userEntity = ConverterUtility.convertUserDTOToEntity(userService.findUserById(userId));
		List<OrderEntity> orderEntityList = orderRepository.findByUserEntity(userEntity);
		List<OrderDTO> orderDTOList = orderEntityList.stream().map(ConverterUtility::convertOrderEntityToDTO)
				.collect(Collectors.toList());
		return orderDTOList;
	}

	private OrderItemEntity createOrderItem(CoinEntity coinEntity, double quantity, double buyPrice, double sellPrice) {
		OrderItemEntity orderItemEntity = new OrderItemEntity();
		orderItemEntity.setCoinEntity(coinEntity);
		orderItemEntity.setQuantity(quantity);
		orderItemEntity.setBuyPrice(buyPrice);
		orderItemEntity.setSellPrice(sellPrice);
		return orderItemRespository.save(orderItemEntity);

	}

	@Transactional
	public OrderDTO buyAsset(CoinEntity coinEntity, double quantity, UserEntity userEntity) throws Exception {
		if (quantity <= 0) {
			throw new Exception("Quantity Should Be Greater Than Zero");
		}
		double buyPrice = coinEntity.getCurrentPrice();
		OrderItemEntity orderItemEntity = createOrderItem(coinEntity, quantity, buyPrice, 0);
		OrderEntity orderEntity = createOrder(userEntity, orderItemEntity, ORDER_TYPE.BUY);
		orderItemEntity.setOrderEntity(orderEntity);
		orderEntity.setOrderStatus(ORDER_STATUS.SUCCESS);
		orderEntity.setOrderType(ORDER_TYPE.BUY);
		orderEntity = orderRepository.save(orderEntity);
		OrderDTO orderDTO = ConverterUtility.convertOrderEntityToDTO(orderEntity);
		UserDTO userDTO = ConverterUtility.convertUserEntityToDTO(userEntity);
		walletService.payOrderpayment(orderDTO, userDTO);

		AssetDTO assetDTO = assetService.findAssetByUserIdAndCoinId(orderDTO.getUserDTO().getId(),
				orderDTO.getOrderItemDTO().getCoinDTO().getId());

		if (assetDTO == null) {
			assetService.createAsset(userEntity, orderItemEntity.getCoinEntity(), orderItemEntity.getQuantity());
		} else {
			assetService.updateAssest(assetDTO.getId(), quantity);
		}
		return orderDTO;

	}

	@Transactional
	public OrderDTO sellAsset(CoinEntity coinEntity, double quantity, UserEntity userEntity) throws Exception {
		if (quantity <= 0) {
			throw new Exception("Quantity Should Be Greater Than Zero");
		}
		double sellPrice = coinEntity.getCurrentPrice();
		AssetDTO assestToSell = assetService.findAssetByUserIdAndCoinId(userEntity.getId(), coinEntity.getId());
		if (assestToSell != null) {

			double buyPrice = assestToSell.getBuyPrice();
			OrderItemEntity orderItemEntity = createOrderItem(coinEntity, quantity, buyPrice, sellPrice);
			OrderEntity orderEntity = createOrder(userEntity, orderItemEntity, ORDER_TYPE.SELL);
			orderItemEntity.setOrderEntity(orderEntity);
			if (assestToSell.getQuantity() >= quantity) {
				orderEntity.setOrderStatus(ORDER_STATUS.SUCCESS);
				orderEntity.setOrderType(ORDER_TYPE.SELL);
				orderEntity = orderRepository.save(orderEntity);
				OrderDTO orderDTO = ConverterUtility.convertOrderEntityToDTO(orderEntity);
				UserDTO userDTO = ConverterUtility.convertUserEntityToDTO(userEntity);
				walletService.payOrderpayment(orderDTO, userDTO);

				AssetDTO updatedAsset = assetService.updateAssest(assestToSell.getId(), -quantity);
				if (updatedAsset.getQuantity() * coinEntity.getCurrentPrice() <= 1) {
					assetService.deleteAsset(updatedAsset.getId());
				}
				return orderDTO;
			}
			throw new Exception("Insufficient Qunatity To Sell");
		}
		throw new Exception("Asset Not Found");

	}

	@Transactional
	@Override
	public OrderDTO processOrder(CoinDTO coinDTO, double quantity, ORDER_TYPE orderType, UserDTO userDTO)
			throws Exception {
		CoinEntity coinEntity = ConverterUtility.convertCoinDTOToEntity(coinDTO);
		UserEntity userEntity = ConverterUtility.convertUserDTOToEntity(userDTO);
		if (orderType.equals(ORDER_TYPE.BUY)) {
			return buyAsset(coinEntity, quantity, userEntity);
		} else if (orderType.equals(ORDER_TYPE.SELL)) {
			return sellAsset(coinEntity, quantity, userEntity);
		}
		throw new Exception("Invalid Order Type");
	}

}
