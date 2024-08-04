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
	public OrderDTO createOrder(UserDTO userDTO, OrderItemDTO orderItemDTO, ORDER_TYPE orderType) {
		double price = orderItemDTO.getCoinDTO().getCurrentPrice() * orderItemDTO.getQuantity();
		OrderEntity orderEntity = new OrderEntity();
		orderEntity.setUserEntity(ConverterUtility.convertUserDTOToEntity(userDTO));
		orderEntity.setOrderItemEntity(ConverterUtility.convertOrderItemDTOToEntity(orderItemDTO));
		orderEntity.setOrderType(orderType);
		orderEntity.setPrice(BigDecimal.valueOf(price));
		orderEntity.setTimestamp(LocalDateTime.now());
		orderEntity.setOrderStatus(ORDER_STATUS.PENDING);
		orderRepository.save(orderEntity);
		return ConverterUtility.convertOrderEntityToDTO(orderEntity);
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

	private OrderItemDTO createOrderItem(CoinDTO coinDTO, double quantity, double buyPrice, double sellPrice) {
		OrderItemEntity orderItemEntity = new OrderItemEntity();
		orderItemEntity.setCoinEntity(ConverterUtility.convertCoinDTOToEntity(coinDTO));
		orderItemEntity.setQuantity(quantity);
		orderItemEntity.setBuyPrice(buyPrice);
		orderItemEntity.setSellPrice(sellPrice);
		orderItemRespository.save(orderItemEntity);
		return ConverterUtility.convertOrderItemEntityToDTO(orderItemEntity);

	}

	@Transactional
	public OrderDTO buyAsset(CoinDTO coinDTO, double quantity, UserDTO userDTO) throws Exception {
		if (quantity <= 0) {
			throw new Exception("Quantity Should Be Greater Than Zero");
		}
		double buyPrice = coinDTO.getCurrentPrice();
		OrderItemDTO ordeItemDTO = createOrderItem(coinDTO, quantity, buyPrice, 0);
		OrderDTO orderDTO = createOrder(userDTO, ordeItemDTO, ORDER_TYPE.BUY);
		ordeItemDTO.setOrderDTO(orderDTO);
		orderDTO.setOrderStatus(ORDER_STATUS.SUCCESS);
		orderDTO.setOrderType(ORDER_TYPE.BUY);
		orderRepository.save(ConverterUtility.convertOrderDTOToEntity(orderDTO));
		walletService.payOrderpayment(orderDTO, userDTO);

		AssetDTO assetDTO = assetService.findAssetByUserIdAndCoinId(orderDTO.getUserDTO().getId(),
				orderDTO.getOrderItemDTO().getCoinDTO().getId());

		if (assetDTO == null) {
			assetService.createAsset(userDTO, ordeItemDTO.getCoinDTO(), ordeItemDTO.getQuantity());
		} else {
			assetService.updateAssest(assetDTO.getId(), quantity);
		}
		return orderDTO;

	}

	@Transactional
	public OrderDTO sellAsset(CoinDTO coinDTO, double quantity, UserDTO userDTO) throws Exception {
		if (quantity <= 0) {
			throw new Exception("Quantity Should Be Greater Than Zero");
		}
		double sellPrice = coinDTO.getCurrentPrice();
		AssetDTO assestToSell = assetService.findAssetByUserIdAndCoinId(userDTO.getId(), coinDTO.getId());
		if (assestToSell != null) {

			double buyPrice = assestToSell.getBuyPrice();
			OrderItemDTO ordeItemDTO = createOrderItem(coinDTO, quantity, buyPrice, sellPrice);
			OrderDTO orderDTO = createOrder(userDTO, ordeItemDTO, ORDER_TYPE.SELL);
			ordeItemDTO.setOrderDTO(orderDTO);
			if (assestToSell.getQuantity() >= quantity) {
				orderDTO.setOrderStatus(ORDER_STATUS.SUCCESS);
				orderDTO.setOrderType(ORDER_TYPE.SELL);
				orderRepository.save(ConverterUtility.convertOrderDTOToEntity(orderDTO));
				walletService.payOrderpayment(orderDTO, userDTO);

				AssetDTO updatedAsset = assetService.updateAssest(assestToSell.getId(), -quantity);
				if (updatedAsset.getQuantity() * coinDTO.getCurrentPrice() <= 1) {
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
		if (orderType.equals(ORDER_TYPE.BUY)) {
			return buyAsset(coinDTO, quantity, userDTO);
		} else if (orderType.equals(ORDER_TYPE.SELL)) {
			return sellAsset(coinDTO, quantity, userDTO);
		}
		throw new Exception("Invalid Order Type");
	}

}
