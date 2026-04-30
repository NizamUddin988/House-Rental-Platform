package com.example.houserental.controller;

import com.example.houserental.model.User;
import com.example.houserental.repository.UserRepository;
import com.example.houserental.service.EmailService;
import com.example.houserental.service.OtpService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private OtpService otpService;

    @Autowired
    private EmailService emailService;

    // ================= OTP =================
    @PostMapping("/send-otp")
    public ResponseEntity<?> sendOtp(@RequestBody Map<String, String> data) {

        String email = data.get("email");

        if (email == null || email.isEmpty()) {
            return ResponseEntity.badRequest().body("Email required");
        }

        if (userRepo.findByEmail(email).isPresent()) {
            return ResponseEntity.status(409).body("Email already registered");
        }

        String otp = otpService.generateOtp(email);
        emailService.sendOtp(email, otp);

        return ResponseEntity.ok("OTP sent successfully");
    }
    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody Map<String, String> data) {

        String email = data.get("email");
        String otp = data.get("otp");

        if (!otpService.verifyOtp(email, otp)) {
            return ResponseEntity.badRequest().body("Invalid or expired OTP");
        }

        User user = new User();
        user.setFullName(data.get("fullName"));
        user.setEmail(email);
        user.setPassword(data.get("password"));
        user.setState(data.get("state"));
        user.setCreatedAt(LocalDateTime.now());

        userRepo.save(user);
        otpService.clearOtp(email);

        return ResponseEntity.ok("Registration successful");
    }

    // ================= LOGIN =================

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginData) {

        String email = loginData.get("email");
        String password = loginData.get("password");

        // Validation
        if (email == null || password == null) {
            return ResponseEntity.badRequest().body("Email and password required");
        }

        Optional<User> userOptional = userRepo.findByEmail(email);

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(401).body("Invalid email or password");
        }

        User user = userOptional.get();

        if (!user.getPassword().equals(password)) {
            return ResponseEntity.status(401).body("Invalid email or password");
        }

        // Success response
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Login successful");
        response.put("userId", user.getId());
        response.put("fullName", user.getFullName());
        response.put("email", user.getEmail());
        response.put("state", user.getState());

        return ResponseEntity.ok(response);
    }
}