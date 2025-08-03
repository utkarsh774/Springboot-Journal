package com.example.journalApp.controller;

import com.example.journalApp.dto.AuthRequest;
import com.example.journalApp.dto.AuthResponse;
import com.example.journalApp.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public AuthResponse register(@RequestBody AuthRequest request) {
        System.out.println("Register API hit with: " + request.getEmail());
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        return authService.login(request);
    }
}
