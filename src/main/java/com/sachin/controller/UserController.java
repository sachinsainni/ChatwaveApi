package com.sachin.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sachin.bean.AuthResponse;
import com.sachin.bean.Login;
import com.sachin.entity.UserLogin;
import com.sachin.security.JwtTokenUtil;
import com.sachin.service.CustomUserDetailsService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@Validated
public class UserController {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private CustomUserDetailsService customUserDetailsService ;

	@PostMapping("/login")
	public ResponseEntity<Object> handleLogin(@Valid @RequestBody Login login, BindingResult bindingResult) {

		// Check for validation errors
		if (bindingResult.hasErrors()) {
			Map<String, String> errors = new HashMap<>();
			for (FieldError error : bindingResult.getFieldErrors()) {
				errors.put(error.getField(), error.getDefaultMessage());
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
		}
		
		 System.out.println("Login Request: " + login);
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));
		System.out.println("authentication");
		System.out.println(authentication);

		String token = jwtTokenUtil.generateToken(authentication);

		AuthResponse authResponse = new AuthResponse(login.getUsername(), token);

		// Return the response
		return ResponseEntity.ok(authResponse);

	}

	@PostMapping("/register")
	public ResponseEntity<Object> createUser(@RequestParam String username, @RequestParam String password,
			@RequestParam String role, @RequestParam String fullName, @RequestParam String email,
			@RequestParam String gender, @RequestParam int age) {
		UserLogin userLogin =  customUserDetailsService.createUser(username, password, role,fullName,email,gender,age);
		return ResponseEntity.ok(userLogin);
	}

	@GetMapping("/test")
	public String test() {
		return "Hello ";
	}

}
