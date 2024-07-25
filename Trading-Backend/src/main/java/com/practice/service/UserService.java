/**
 * @author arif.shaikh 16-Jun-2024
 */
package com.practice.service;

import java.util.ArrayList;
import java.util.List;

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
import com.practice.dto.TwoFactorOTPDTO;
import com.practice.dto.UserDTO;
import com.practice.entity.UserEntity;
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
			TwoFactorOTPDTO oldTwoFactorOTPDTO = twoFactorOTPService.findByUser(userEntity.getId());
			if(oldTwoFactorOTPDTO!=null) {
				twoFactorOTPService.deleteTwoFactorOtp(oldTwoFactorOTPDTO);
			}
			TwoFactorOTPDTO newTwoFactorOTPDTO = twoFactorOTPService.createTwoFactorOtp(userDTO, otp, jwt);
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

}
