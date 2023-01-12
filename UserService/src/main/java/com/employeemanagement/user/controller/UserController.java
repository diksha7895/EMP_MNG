package com.employeemanagement.user.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.employeemanagement.user.jwt.JwtUtils;
import com.employeemanagement.user.jwt.UserDetailsImpl;
import com.employeemanagement.user.model.User;
import com.employeemanagement.user.payload.request.LoginRequest;
import com.employeemanagement.user.payload.request.RegisterRequest;
import com.employeemanagement.user.payload.response.JwtResponse;
import com.employeemanagement.user.payload.response.MessageResponse;
import com.employeemanagement.user.repository.UserRepository;

public class UserController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	JwtUtils jwtUtils;
	
	//new user registration
	@PostMapping("/RegisterUser")
	public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
		if (userRepository.existsByUsername(registerRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already exist."));
		}

		if (userRepository.existsByEmail(registerRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already exist."));
		}

		// Create new user's account
		User user = new User(registerRequest.getFirstname(),
							registerRequest.getLastname(),
							registerRequest.getUsername(),
							registerRequest.getEmail(),
							encoder.encode(registerRequest.getPassword()));


		//user.setRoles(roles);
		userRepository.save(user);

		return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("User registered successfully!"));
	}
	
	
	//user login
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt, 
												 userDetails.getId(), 
												 userDetails.getUsername(), 
												 userDetails.getEmail(), 
												 roles));
	}

	
}
