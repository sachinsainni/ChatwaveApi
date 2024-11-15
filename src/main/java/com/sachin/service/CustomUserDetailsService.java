package com.sachin.service;

import com.sachin.entity.UserLogin;
import com.sachin.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
    private UserRepository userRepository;

    

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	System.out.println("username -- "+username);
        UserLogin user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        
        System.out.println(user);

        return User.builder()
                .username(user.getUsername())
                .password(new BCryptPasswordEncoder().encode(user.getPassword()))
                .roles(user.getRole())  // Assuming role is a single string like "ADMIN"
                .build();
    }
    
    public UserLogin createUser(String username, String password, String role, String fullName,String email, String gender, int age) {
        UserLogin user = new UserLogin();
        user.setUsername(username);
        user.setPassword(password);  // Note: Consider hashing the password before saving
        user.setRole(role);
        user.setEmail(email);
        user.setGender(gender);
        user.setFullName(fullName);
        user.setAge(age);
        
        return userRepository.save(user);
    }
    
}
