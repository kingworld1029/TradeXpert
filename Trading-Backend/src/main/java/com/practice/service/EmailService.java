/**
 * @author arif.shaikh 25-Jul-2024
 */
package com.practice.service;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

/**
 * 
 */
@Service
public class EmailService implements IEmailService {

	
	private JavaMailSender javaMailSender;

	@Override
	public void sendVerificationOtpEmail(String email, String otp) {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "utf-8");
		String subject = "Verify OTP";
		String text = "Your Verification Code is "+ otp;
		try {
			mimeMessageHelper.setSubject(subject);
			mimeMessageHelper.setText(text);
			mimeMessageHelper.setTo(email);
			javaMailSender.send(mimeMessage);
		} catch (MessagingException | MailException e) {
			throw new MailSendException(e.getMessage());
		}
		
	}
}
