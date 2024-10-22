/**
 * @author arif.shaikh 03-Aug-2024
 */
package com.practice.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;

import com.practice.dto.AssetDTO;
import com.practice.dto.CoinDTO;
import com.practice.dto.OrderDTO;
import com.practice.dto.OrderItemDTO;
import com.practice.dto.PaymentDetailDTO;
import com.practice.dto.PaymentOrderDTO;
import com.practice.dto.TwoFactorOTPDTO;
import com.practice.dto.UserDTO;
import com.practice.dto.VerificationCodeDTO;
import com.practice.dto.WalletDTO;
import com.practice.dto.WalletTransactionDTO;
import com.practice.dto.WatchListDTO;
import com.practice.dto.WithdrawalDTO;
import com.practice.entity.AssetEntity;
import com.practice.entity.CoinEntity;
import com.practice.entity.OrderEntity;
import com.practice.entity.OrderItemEntity;
import com.practice.entity.PaymentDetailEntity;
import com.practice.entity.PaymentOrderEntity;
import com.practice.entity.TwoFactorOTPEntity;
import com.practice.entity.UserEntity;
import com.practice.entity.VerificationCodeEntity;
import com.practice.entity.WalletEntity;
import com.practice.entity.WalletTransactionEntity;
import com.practice.entity.WatchListEntity;
import com.practice.entity.WithdrawalEntity;

/**
 * 
 */
public class ConverterUtility {

	public static UserDTO convertUserEntityToDTO(UserEntity userEntity) {
		UserDTO userDTO = new UserDTO();
		if (userEntity != null) {
			BeanUtils.copyProperties(userEntity, userDTO);
			return userDTO;
		}
		return null;

	}

	public static UserEntity convertUserDTOToEntity(UserDTO userDTO) {
		UserEntity userEntity = new UserEntity();
		if (userDTO != null) {
			BeanUtils.copyProperties(userDTO, userEntity);
			return userEntity;
		}
		return null;

	}

	public static WalletDTO convertWalletEntityToDTO(WalletEntity walletEntity) {
		WalletDTO walletDTO = new WalletDTO();
		if (walletEntity != null) {
			BeanUtils.copyProperties(walletEntity, walletDTO);
			if (walletEntity.getUserEntity() != null) {
				walletDTO.setUserDTO(convertUserEntityToDTO(walletEntity.getUserEntity()));
			}
			return walletDTO;
		}
		return null;

	}

	public static WalletEntity convertWalletDTOToEntity(WalletDTO walletDTO) {
		WalletEntity walletEntity = new WalletEntity();
		if (walletDTO != null) {
			BeanUtils.copyProperties(walletDTO, walletEntity);
			if (walletDTO.getUserDTO() != null) {
				walletEntity.setUserEntity(convertUserDTOToEntity(walletDTO.getUserDTO()));
			}
			return walletEntity;
		}
		return null;

	}

	public static TwoFactorOTPDTO convertOTPEntityToDTO(TwoFactorOTPEntity twoFactorOTPEntity) {
		TwoFactorOTPDTO twoFactorOTPDTO = new TwoFactorOTPDTO();
		if (twoFactorOTPEntity != null) {
			BeanUtils.copyProperties(twoFactorOTPEntity, twoFactorOTPDTO);
			if (twoFactorOTPEntity.getUserEntity() != null) {
				twoFactorOTPDTO.setUserDTO(convertUserEntityToDTO(twoFactorOTPEntity.getUserEntity()));
			}
			return twoFactorOTPDTO;
		}
		return null;

	}

	public static TwoFactorOTPEntity convertOTPDTOToEntity(TwoFactorOTPDTO twoFactorOTPDTO) {
		TwoFactorOTPEntity twoFactorOTPEntity = new TwoFactorOTPEntity();
		if (twoFactorOTPDTO != null) {
			BeanUtils.copyProperties(twoFactorOTPDTO, twoFactorOTPEntity);
			if (twoFactorOTPDTO.getUserDTO() != null) {
				twoFactorOTPEntity.setUserEntity(convertUserDTOToEntity(twoFactorOTPDTO.getUserDTO()));
			}
			return twoFactorOTPEntity;
		}
		return null;

	}

	public static VerificationCodeDTO convertCodeEntityToDTO(VerificationCodeEntity verificationCodeEntity) {
		VerificationCodeDTO verificationCodeDTO = new VerificationCodeDTO();
		if (verificationCodeEntity != null) {
			BeanUtils.copyProperties(verificationCodeEntity, verificationCodeDTO);
			if (verificationCodeEntity.getUserEntity() != null) {
				verificationCodeDTO.setUserDTO(convertUserEntityToDTO(verificationCodeEntity.getUserEntity()));
			}
			return verificationCodeDTO;
		}
		return null;

	}

	public static VerificationCodeEntity convertCodeDTOToEntity(VerificationCodeDTO verificationCodeDTO) {
		VerificationCodeEntity verificationCodeEntity = new VerificationCodeEntity();
		if (verificationCodeDTO != null) {
			BeanUtils.copyProperties(verificationCodeDTO, verificationCodeEntity);
			if (verificationCodeDTO.getUserDTO() != null) {
				verificationCodeEntity.setUserEntity(convertUserDTOToEntity(verificationCodeDTO.getUserDTO()));
			}
			return verificationCodeEntity;
		}
		return null;

	}

	public static OrderItemDTO convertOrderItemEntityToDTO(OrderItemEntity orderItemEntity) {
		OrderItemDTO orderItemDTO = new OrderItemDTO();
		if (orderItemEntity != null) {
			BeanUtils.copyProperties(orderItemEntity, orderItemDTO);
			if (orderItemEntity.getCoinEntity() != null) {
				orderItemDTO.setCoinDTO(convertCoinEntityToDTO(orderItemEntity.getCoinEntity()));
			}
			return orderItemDTO;
		}
		return null;

	}

	public static OrderItemEntity convertOrderItemDTOToEntity(OrderItemDTO orderItemDTO) {
		OrderItemEntity orderItemEntity = new OrderItemEntity();
		if (orderItemDTO != null) {
			BeanUtils.copyProperties(orderItemDTO, orderItemEntity);
			if (orderItemDTO.getCoinDTO() != null) {
				orderItemEntity.setCoinEntity(convertCoinDTOToEntity(orderItemDTO.getCoinDTO()));
			}
			return orderItemEntity;
		}
		return null;

	}

	public static OrderDTO convertOrderEntityToDTO(OrderEntity orderEntity) {
		OrderDTO orderDTO = new OrderDTO();
		if (orderEntity != null) {
			BeanUtils.copyProperties(orderEntity, orderDTO);
			if (orderEntity.getUserEntity() != null) {
				orderDTO.setUserDTO(convertUserEntityToDTO(orderEntity.getUserEntity()));
			}
			if (orderEntity.getOrderItemEntity() != null) {
				orderDTO.setOrderItemDTO(convertOrderItemEntityToDTO(orderEntity.getOrderItemEntity()));
			}
			return orderDTO;
		}
		return null;

	}

	public static OrderEntity convertOrderDTOToEntity(OrderDTO orderDTO) {
		OrderEntity orderEntity = new OrderEntity();
		if (orderDTO != null) {
			BeanUtils.copyProperties(orderDTO, orderEntity);
			if (orderDTO.getUserDTO() != null) {
				orderEntity.setUserEntity(convertUserDTOToEntity(orderDTO.getUserDTO()));
			}
			if (orderDTO.getOrderItemDTO() != null) {
				orderEntity.setOrderItemEntity(convertOrderItemDTOToEntity(orderDTO.getOrderItemDTO()));
			}
			return orderEntity;
		}
		return null;

	}

	public static CoinDTO convertCoinEntityToDTO(CoinEntity coinEntity) {
		CoinDTO coinDTO = new CoinDTO();
		if (coinEntity != null) {
			BeanUtils.copyProperties(coinEntity, coinDTO);
			return coinDTO;
		}
		return null;

	}

	public static CoinEntity convertCoinDTOToEntity(CoinDTO coinDTO) {
		CoinEntity coinEntity = new CoinEntity();
		if (coinDTO != null) {
			BeanUtils.copyProperties(coinDTO, coinEntity);
			return coinEntity;
		}
		return null;

	}

	public static AssetDTO convertAssetEntityToDTO(AssetEntity assetEntity) {
		AssetDTO assetDTO = new AssetDTO();
		if (assetEntity != null) {
			BeanUtils.copyProperties(assetEntity, assetDTO);
			if (assetEntity.getUserEntity() != null) {
				assetDTO.setUserDTO(convertUserEntityToDTO(assetEntity.getUserEntity()));
			}
			if (assetEntity.getCoinEntity() != null) {
				assetDTO.setCoinDTO(convertCoinEntityToDTO(assetEntity.getCoinEntity()));
			}
			return assetDTO;
		}
		return null;

	}

	public static AssetEntity convertAssetDTOToEntity(AssetDTO assetDTO) {
		AssetEntity assetEntity = new AssetEntity();
		if (assetDTO != null) {
			BeanUtils.copyProperties(assetDTO, assetEntity);
			if (assetDTO.getUserDTO() != null) {
				assetEntity.setUserEntity(convertUserDTOToEntity(assetDTO.getUserDTO()));
			}
			if (assetDTO.getCoinDTO() != null) {
				assetEntity.setCoinEntity(convertCoinDTOToEntity(assetDTO.getCoinDTO()));
			}
			return assetEntity;
		}
		return null;

	}

	public static WithdrawalDTO convertWithdrawalEntityToDTO(WithdrawalEntity withdrawalEntity) {
		WithdrawalDTO withdrawalDTO = new WithdrawalDTO();
		if (withdrawalEntity != null) {
			BeanUtils.copyProperties(withdrawalEntity, withdrawalDTO);
			if (withdrawalEntity.getUserEntity() != null) {
				withdrawalDTO.setUserDTO(convertUserEntityToDTO(withdrawalEntity.getUserEntity()));
			}
			return withdrawalDTO;
		}
		return null;

	}

	public static WithdrawalEntity convertWithdrawalDTOToEntity(WithdrawalDTO withdrawalDTO) {
		WithdrawalEntity withdrawalEntity = new WithdrawalEntity();
		if (withdrawalDTO != null) {
			BeanUtils.copyProperties(withdrawalDTO, withdrawalEntity);
			if (withdrawalDTO.getUserDTO() != null) {
				withdrawalEntity.setUserEntity(convertUserDTOToEntity(withdrawalDTO.getUserDTO()));
			}
			return withdrawalEntity;
		}
		return null;

	}

	public static WatchListDTO convertWatchListEntityToDTO(WatchListEntity watchListEntity) {
		WatchListDTO watchListDTO = new WatchListDTO();
		if (watchListEntity != null) {
			watchListDTO.setId(watchListEntity.getId());
			if (watchListEntity.getUserEntity() != null) {
				watchListDTO.setUserDTO(convertUserEntityToDTO(watchListEntity.getUserEntity()));
			}
			if (!watchListEntity.getCoinEntities().isEmpty()) {
				List<CoinDTO> coinDTOList = watchListEntity.getCoinEntities().stream().map(coinEntity -> {
					CoinDTO coinDTO = new CoinDTO();
					BeanUtils.copyProperties(coinEntity, coinDTO);
					return coinDTO;
				}).collect(Collectors.toList());
				watchListDTO.setCoinDTOs(coinDTOList);
			}
			return watchListDTO;
		}
		return null;

	}

	public static WatchListEntity convertWatchListDTOToEntity(WatchListDTO watchListDTO) {
		WatchListEntity watchListEntity = new WatchListEntity();
		if (watchListDTO != null) {
			watchListEntity.setId(watchListDTO.getId());
			if (watchListDTO.getUserDTO() != null) {
				watchListEntity.setUserEntity(convertUserDTOToEntity(watchListDTO.getUserDTO()));
			}
			if (!watchListDTO.getCoinDTOs().isEmpty()) {
				List<CoinEntity> coinEntityList = watchListDTO.getCoinDTOs().stream().map(coinDto -> {
					CoinEntity coinEntity = new CoinEntity();
					BeanUtils.copyProperties(coinDto, coinEntity);
					return coinEntity;
				}).collect(Collectors.toList());
				watchListEntity.setCoinEntities(coinEntityList);
			}

			return watchListEntity;
		}
		return null;

	}

	public static PaymentDetailDTO convertPaymentDetailEntityToDTO(PaymentDetailEntity paymentDetailEntity) {
		PaymentDetailDTO paymentDetailDTO = new PaymentDetailDTO();
		if (paymentDetailEntity != null) {
			BeanUtils.copyProperties(paymentDetailEntity, paymentDetailDTO);
			if (paymentDetailEntity.getUserEntity() != null) {
				paymentDetailDTO.setUserDTO(convertUserEntityToDTO(paymentDetailEntity.getUserEntity()));
			}
			return paymentDetailDTO;
		}
		return null;

	}

	public static PaymentDetailEntity convertPaymentDetailDTOToEntity(PaymentDetailDTO paymentDetailDTO) {
		PaymentDetailEntity paymentDetailEntity = new PaymentDetailEntity();
		if (paymentDetailDTO != null) {
			BeanUtils.copyProperties(paymentDetailDTO, paymentDetailEntity);
			if (paymentDetailDTO.getUserDTO() != null) {
				paymentDetailEntity.setUserEntity(convertUserDTOToEntity(paymentDetailDTO.getUserDTO()));
			}
			return paymentDetailEntity;
		}
		return null;

	}

	public static PaymentOrderDTO convertPaymentOrderEntityToDTO(PaymentOrderEntity paymentOrderEntity) {
		PaymentOrderDTO paymentOrderDTO = new PaymentOrderDTO();
		if (paymentOrderEntity != null) {
			BeanUtils.copyProperties(paymentOrderEntity, paymentOrderDTO);
			if (paymentOrderEntity.getUserEntity() != null) {
				paymentOrderDTO.setUserDTO(convertUserEntityToDTO(paymentOrderEntity.getUserEntity()));
			}
			return paymentOrderDTO;
		}
		return null;

	}

	public static PaymentOrderEntity convertPaymentOrderDTOToEntity(PaymentOrderDTO paymentOrderDTO) {
		PaymentOrderEntity paymentOrderEntity = new PaymentOrderEntity();
		if (paymentOrderDTO != null) {
			BeanUtils.copyProperties(paymentOrderDTO, paymentOrderEntity);
			if (paymentOrderDTO.getUserDTO() != null) {
				paymentOrderEntity.setUserEntity(convertUserDTOToEntity(paymentOrderDTO.getUserDTO()));
			}
			return paymentOrderEntity;
		}
		return null;

	}

	public static WalletTransactionEntity convertWalletTransDTOToEntity(WalletTransactionDTO walletTransactionDTO) {
		WalletTransactionEntity walletTransactionEntity = new WalletTransactionEntity();
		if (walletTransactionDTO != null) {
			BeanUtils.copyProperties(walletTransactionDTO, walletTransactionEntity);
			if (walletTransactionDTO.getWalletDTO() != null) {
				walletTransactionEntity.setWalletEntity(convertWalletDTOToEntity(walletTransactionDTO.getWalletDTO()));
			}
			return walletTransactionEntity;
		}
		return null;

	}

	public static WalletTransactionDTO convertWalletTransEntityToDTO(WalletTransactionEntity walletTransactionEntity) {
		WalletTransactionDTO walletTransactionDTO = new WalletTransactionDTO();
		if (walletTransactionEntity != null) {
			BeanUtils.copyProperties(walletTransactionEntity, walletTransactionDTO);
			if (walletTransactionEntity.getWalletEntity() != null) {
				walletTransactionDTO.setWalletDTO(convertWalletEntityToDTO(walletTransactionEntity.getWalletEntity()));
			}
			return walletTransactionDTO;
		}
		return null;

	}

}
