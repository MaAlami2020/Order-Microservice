package com.example.webapp1a.controller.auth;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.webapp1a.data.AuthResponse;
import com.example.webapp1a.model.User;
import com.example.webapp1a.repository.UserRepo;
import com.example.webapp1a.security.JWTGenerator;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

	private AuthenticationManager authenticationManager;
	private JWTGenerator jwtGenerator;

	@Autowired
	public LoginController(AuthenticationManager authenticationManager, UserRepo userRepo,
						PasswordEncoder passwordEncoder, JWTGenerator jwtGenerator){
		this.authenticationManager = authenticationManager;	
		this.jwtGenerator = jwtGenerator;				
	}

	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@RequestBody User user){

		Authentication authentication = authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = jwtGenerator.generateToken(authentication);

		return new ResponseEntity<>(new AuthResponse(token),HttpStatus.OK);
	
	}

	@PostMapping("/logout")
	public ResponseEntity<String> logout() {

		SecurityContextHolder.clearContext();

		return new ResponseEntity<>("logout successfully",HttpStatus.OK);
	}
}
