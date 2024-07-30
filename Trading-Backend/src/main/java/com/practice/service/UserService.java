/**
 * @author arif.shaikh 16-Jun-2024
 */
package com.practice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.practice.dto.TwoFactorAuthDTO;
import com.practice.dto.TwoFactorOTPDTO;
import com.practice.dto.UserDTO;
import com.practice.entity.TwoFactorAuthEntity;
import com.practice.entity.TwoFactorOTPEntity;
import com.practice.entity.UserEntity;
import com.practice.helper.HelperEnum.VERIFICATION_TYPE;
import com.practice.repository.UserRepository;
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

	@Override
	public AuthResponse register(UserDTO userDTO) throws Exception {
		UserEntity userEntity = new UserEntity();
		
		UserEntity isUserExist = userRepository.findByEmail(userDTO.getEmail());
		if(isUserExist!=null) {
			throw new Exception("Email ALready Registered");
		}
		
		userEntity.setFullName(userDTO.getFullName());
		userEntity.setEmail(userDTO.getEmail());
		String hashedPassword = passwordEncoder.encode(userDTO.getPassword());
		userEntity.setPassword(hashedPassword);
		userEntity.setMobileNo(userDTO.getMobileNo());
		userEntity = userRepository.save(userEntity);
		Authentication auth = new UsernamePasswordAuthenticationToken(userDTO.getEmail(),userDTO.getPassword());
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
		if(userEntity==null) {
			throw new UsernameNotFoundException(email);
		}
		List<GrantedAuthority> authorityList = new ArrayList<>();
		return new User(userEntity.getEmail(),userEntity.getPassword(),authorityList);
	}

	@Override
	public AuthResponse login(UserDTO userDTO) {
		Authentication auth = authenticate(userDTO.getEmail(),userDTO.getPassword());
		SecurityContextHolder.getContext().setAuthentication(auth);
		String jwt = JwtProvider.generateToken(auth);
		UserEntity userEntity = userRepository.findByEmail(userDTO.getEmail());
		userDTO.setId(userEntity.getId());
		if(userDTO.getTwoFactorAuthDTO().isEnabled()) {
			AuthResponse response = new AuthResponse();
			response.setMessage("Two Factor Auth is Enabled");
			response.setTwoFactorAuthEnable(true);
			String otp = OtpUtility.generateOtp();
			TwoFactorOTPDTO oldTwoFactorOTPDTO = twoFactorOTPService.findByUser(userEntity);
			if(oldTwoFactorOTPDTO!=null) {
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
		UserDetails userDetails  = loadUserByUsername(email);
		if(userDetails==null) {
			throw new BadCredentialsException("Invalid Email");
		}
		if(!passwordEncoder.matches(password, userDetails.getPassword())){
			throw new BadCredentialsException("Invalid PassWord");
		}
		return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
	}

	@Override
	public AuthResponse verifySigningOtp(String otp, String id) throws Exception {
		TwoFactorOTPDTO twoFactorOTPDTO = twoFactorOTPService.findById(id);
		if(twoFactorOTPService.verifyTwoFactorOtp(twoFactorOTPDTO, otp)) {
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
		if(userEntity==null) {
			throw new Exception("User Not Found");
		}
		return covertEntityToDTO(userEntity);
	}

	@Override
	public UserDTO findUserById(Long userId) throws Exception {
		Optional<UserEntity> userEntity=userRepository.findById(userId);
		if(userEntity.isEmpty()) {
			throw new Exception("User Not Found");
		}
		return covertEntityToDTO(userEntity.get());
	}

	@Override
	public UserDTO enableTwoFactorAuthentocation(VERIFICATION_TYPE verificationType, String SendTo,UserDTO userDTO) {
		UserEntity userEntity = new UserEntity();
		TwoFactorAuthEntity twoFactorAuthEntity  = new TwoFactorAuthEntity();
		BeanUtils.copyProperties(userDTO, userEntity);
		twoFactorAuthEntity.setEnabled(true);
		twoFactorAuthEntity.setSendTO(verificationType);
		userEntity.setTwoFactorAuthEntity(twoFactorAuthEntity);
		userEntity = userRepository.save(userEntity);
		return covertEntityToDTO(userEntity);
	}

	@Override
	public UserDTO updatepassword(UserDTO userDTO, String newPassword) {
		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(userDTO, userEntity);
		userEntity.setPassword(newPassword);
		userEntity = userRepository.save(userEntity);
		return covertEntityToDTO(userEntity);
	}
	
	public UserDTO covertEntityToDTO(UserEntity userEntity){
		UserDTO userDTO =new UserDTO();
		if(userEntity!=null) {
			BeanUtils.copyProperties(userEntity, userDTO);
			return userDTO;
		}
		return null;
		
	}

}
