/**
 * @author arif.shaikh 21-Jul-2024
 */
package com.practice.utils;

import java.util.Random;

/**
 * 
 */
public class OtpUtility {
	
	public static String generateOtp() {
		int otpLength  = 6;
		Random random = new Random();
		StringBuilder otp = new StringBuilder(otpLength);
		for(int i= 0 ;i<otpLength;i++) {
			otp.append(random.nextInt(10));
		}
		return otp.toString();
	}

}
