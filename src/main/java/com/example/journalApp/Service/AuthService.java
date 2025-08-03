package com.example.journalApp.Service;

import com.example.journalApp.dto.AuthRequest;
import com.example.journalApp.dto.AuthResponse;
import com.example.journalApp.Model.UserModel;
import com.example.journalApp.Repository.UserRepository;
import com.example.journalApp.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    // REGISTER: Create a new user
    public AuthResponse register(AuthRequest request) {
        Optional<UserModel> existingUser = userRepository.findByEmail(request.getEmail());

        if (existingUser.isPresent()) {
            throw new RuntimeException("User already exists");
        } else {
            UserModel newUser = new UserModel();
            newUser.setName(request.getUsername());
            newUser.setEmail(request.getEmail());
            newUser.setPassword(passwordEncoder.encode(request.getPassword()));
            userRepository.save(newUser);

            String token = jwtService.generateToken(newUser);
            return new AuthResponse(token);
        }
    }

    // LOGIN: Authenticate existing user
    public AuthResponse login(AuthRequest request) {
        Optional<UserModel> userOptional = userRepository.findByEmail(request.getEmail());

        if (userOptional.isPresent()) {
            UserModel user = userOptional.get();
            boolean passwordMatch = passwordEncoder.matches(request.getPassword(), user.getPassword());

            if (passwordMatch) {
                String token = jwtService.generateToken(user);
                return new AuthResponse(token);
            } else {
                throw new RuntimeException("Incorrect password");
            }

        } else {
            throw new RuntimeException("User not found");
        }
    }
}
