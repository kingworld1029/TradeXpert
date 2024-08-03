/**
 * @author arif.shaikh 16-Jun-2024
 */
package com.practice.helper;

/**
 * 
 */
public class HelperEnum {

	public enum USER_ROLE {
		ROLE_ADMIN, ROLE_CUSTOMER

	}

	public enum VERIFICATION_TYPE {
		MOBILE, EMAIL

	}

	public enum ORDER_TYPE {
		BUY, SELL
	}

	public enum ORDER_STATUS {
		PENDING, FAILED, CANCELLED, PARTIALLY_FAILED, ERROR, SUCCESS
	}

	public enum WALLET_TRANS_TYPE {
		WITHDRAWAL, WALLET_TRANSFER, ADD_MONEY, BUY_ASSET, SELL_ASSET
	}
}
