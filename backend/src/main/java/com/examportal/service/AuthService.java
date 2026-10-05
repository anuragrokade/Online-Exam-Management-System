package com.examportal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.examportal.dto.LoginRequest;
import com.examportal.dto.LoginResponse;
import com.examportal.dto.RegisterRequest;
import com.examportal.entity.User;
import com.examportal.repository.UserRepository;


@Service
public class AuthService {

    @Autowired 
    private UserRepository userRepository;
 
    @Autowired
    private PasswordEncoder passwordEncoder; 
    
//    @Autowired
//    private JwtService jwtService;

    public String register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            return "Email already exists";
        }

        User user = new User();

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());

        // Encrypt password before saving
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setRole(request.getRole());

        userRepository.save(user);

        return "User registered successfully";
    }

    public LoginResponse login(LoginRequest request) { 

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        boolean matched =
                passwordEncoder.matches(
                        request.getPassword(),
                        user.getPassword());

        if (!matched) {
            throw new RuntimeException("Invalid Credentials");
        }

//        String token =
//                jwtService.generateToken(user.getEmail());
  
        return new LoginResponse(
                user.getId(),
                user.getEmail(),
                user.getRole().name()); 
    }
    
}