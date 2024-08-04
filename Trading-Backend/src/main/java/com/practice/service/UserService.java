/**
 * @author arif.shaikh 16-Jun-2024
 */
package com.practice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.practice.config.JwtProvider;
import com.practice.dto.AuthResponse;
import com.practice.dto.ForgotPasswordTokenDTO;
import com.practice.dto.TwoFactorOTPDTO;
import com.practice.dto.UserDTO;
import com.practice.entity.TwoFactorAuthEntity;
import com.practice.entity.UserEntity;
import com.practice.helper.HelperEnum.VERIFICATION_TYPE;
import com.practice.repository.UserRepository;
import com.practice.utils.ConverterUtility;
import com.practice.utils.OtpUtility;

/**
 * 
 */
@Service
public class UserService implements IUserService, UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private ITwoFactorOTPService twoFactorOTPService;

	@Autowired
	private IEmailService emailService;

	@Autowired
	private IForgotPasswordService forgotPasswordService;

	@Autowired
	private IWatchListService watchListService;

	@Override
	public AuthResponse register(UserDTO userDTO) throws Exception {
		UserEntity userEntity = new UserEntity();

		UserEntity isUserExist = userRepository.findByEmail(userDTO.getEmail());
		if (isUserExist != null) {
			throw new Exception("Email ALready Registered");
		}

		userEntity.setFullName(userDTO.getFullName());
		userEntity.setEmail(userDTO.getEmail());
		String hashedPassword = passwordEncoder.encode(userDTO.getPassword());
		userEntity.setPassword(hashedPassword);
		userEntity.setMobileNo(userDTO.getMobileNo());
		userEntity = userRepository.save(userEntity);
		userDTO.setId(userEntity.getId());
		watchListService.createWatchlist(userDTO);
		Authentication auth = new UsernamePasswordAuthenticationToken(userDTO.getEmail(), userDTO.getPassword());
		SecurityContextHolder.getContext().setAuthentication(auth);
		String jwt = JwtProvider.generateToken(auth);
		AuthResponse response = new AuthResponse();
		response.setJwt(jwt);
		response.setStatus(true);
		response.setMessage("Register SucessFully");
		return response;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findByEmail(email);
		if (userEntity == null) {
			throw new UsernameNotFoundException(email);
		}
		List<GrantedAuthority> authorityList = new ArrayList<>();
		return new User(userEntity.getEmail(), userEntity.getPassword(), authorityList);
	}

	@Override
	public AuthResponse login(UserDTO userDTO) {
		Authentication auth = authenticate(userDTO.getEmail(), userDTO.getPassword());
		SecurityContextHolder.getContext().setAuthentication(auth);
		String jwt = JwtProvider.generateToken(auth);
		UserEntity userEntity = userRepository.findByEmail(userDTO.getEmail());
		userDTO.setId(userEntity.getId());
		if (userDTO.getTwoFactorAuthDTO().isEnabled()) {
			AuthResponse response = new AuthResponse();
			response.setMessage("Two Factor Auth is Enabled");
			response.setTwoFactorAuthEnable(true);
			String otp = OtpUtility.generateOtp();
			TwoFactorOTPDTO oldTwoFactorOTPDTO = twoFactorOTPService.findByUser(userEntity);
			if (oldTwoFactorOTPDTO != null) {
				twoFactorOTPService.deleteTwoFactorOtp(oldTwoFactorOTPDTO);
			}
			TwoFactorOTPDTO newTwoFactorOTPDTO = twoFactorOTPService.createTwoFactorOtp(userDTO, otp, jwt);
			emailService.sendVerificationOtpEmail(userDTO.getEmail(), otp);
			response.setSession(newTwoFactorOTPDTO.getId());
			return response;
		}
		AuthResponse response = new AuthResponse();
		response.setJwt(jwt);
		response.setStatus(true);
		response.setMessage("Login SucessFully");
		return response;
	}

	/**
	 * @param email
	 * @param password
	 * @return
	 */
	private Authentication authenticate(String email, String password) {
		UserDetails userDetails = loadUserByUsername(email);
		if (userDetails == null) {
			throw new BadCredentialsException("Invalid Email");
		}
		if (!passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new BadCredentialsException("Invalid PassWord");
		}
		return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
	}

	@Override
	public AuthResponse verifySigningOtp(String otp, String id) throws Exception {
		TwoFactorOTPDTO twoFactorOTPDTO = twoFactorOTPService.findById(id);
		if (twoFactorOTPService.verifyTwoFactorOtp(twoFactorOTPDTO, otp)) {
			AuthResponse response = new AuthResponse();
			response.setMessage("Two Factor OTP authentication Verified");
			response.setTwoFactorAuthEnable(true);
			response.setJwt(twoFactorOTPDTO.getJwt());
			return response;
		}
		throw new Exception("Invalid OTP");
	}

	@Override
	public UserDTO findUserProfileByJwt(String jwt) throws Exception {
		String email = JwtProvider.getEmailFromToken(jwt);
		return findUserByEmail(email);
	}

	@Override
	public UserDTO findUserByEmail(String email) throws Exception {
		UserEntity userEntity = userRepository.findByEmail(email);
		if (userEntity == null) {
			throw new Exception("User Not Found");
		}
		return ConverterUtility.convertUserEntityToDTO(userEntity);
	}

	@Override
	public UserDTO findUserById(Long userId) throws Exception {
		Optional<UserEntity> userEntity = userRepository.findById(userId);
		if (userEntity.isEmpty()) {
			throw new Exception("User Not Found");
		}
		return ConverterUtility.convertUserEntityToDTO(userEntity.get());
	}

	@Override
	public UserDTO enableTwoFactorAuthentocation(VERIFICATION_TYPE verificationType, String SendTo, UserDTO userDTO) {
		UserEntity userEntity = new UserEntity();
		TwoFactorAuthEntity twoFactorAuthEntity = new TwoFactorAuthEntity();
		BeanUtils.copyProperties(userDTO, userEntity);
		twoFactorAuthEntity.setEnabled(true);
		twoFactorAuthEntity.setSendTO(verificationType);
		userEntity.setTwoFactorAuthEntity(twoFactorAuthEntity);
		userEntity = userRepository.save(userEntity);
		return ConverterUtility.convertUserEntityToDTO(userEntity);
	}

	@Override
	public UserDTO updatepassword(UserDTO userDTO, String newPassword) {
		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(userDTO, userEntity);
		userEntity.setPassword(newPassword);
		userEntity = userRepository.save(userEntity);
		return ConverterUtility.convertUserEntityToDTO(userEntity);
	}

	@Override
	public AuthResponse sendForgotPasswordOtp(ForgotPasswordTokenDTO requestDTO) throws Exception {
		UserDTO userDTO = findUserByEmail(requestDTO.getSendTo());
		String otp = OtpUtility.generateOtp();
		UUID uuid = UUID.randomUUID();
		String id = uuid.toString();
		ForgotPasswordTokenDTO forgotPasswordTokenDTO = forgotPasswordService.findByUser(userDTO);
		if (forgotPasswordTokenDTO == null) {
			forgotPasswordTokenDTO = forgotPasswordService.createToken(userDTO, id, otp,
					requestDTO.getVerificationType(), requestDTO.getSendTo());
		}
		if (requestDTO.getVerificationType().equals(VERIFICATION_TYPE.EMAIL)) {
			emailService.sendVerificationOtpEmail(userDTO.getEmail(), forgotPasswordTokenDTO.getOtp());
		}
		AuthResponse response = new AuthResponse();
		response.setSession(forgotPasswordTokenDTO.getId());
		response.setMessage("Forget Password Otp Sent SuccessFully");
		return response;
	}

	@Override
	public AuthResponse verifyForgotPasswordOtp(ForgotPasswordTokenDTO requestDTO, String id) throws Exception {
		ForgotPasswordTokenDTO forgotPasswordTokenDTO = forgotPasswordService.findById(id);
		boolean isVerified = forgotPasswordTokenDTO.getOtp().equals(requestDTO.getOtp());
		if (isVerified) {
			updatepassword(forgotPasswordTokenDTO.getUserDTO(), requestDTO.getPassword());
			AuthResponse response = new AuthResponse();
			response.setMessage("Password Updated SuccessFully");
			return response;
		}
		throw new Exception("Wrong OTP");
	}

}
