package com.example.journalApp.controller;


import com.example.journalApp.Model.UserModel;
import com.example.journalApp.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {


    @Autowired
    private  UserRepository userRepository;




    // GET Users by id
    @GetMapping("/{id}")
    public ResponseEntity<UserModel> getUserById(@PathVariable String id) {
        Optional<UserModel> user = userRepository.findById(id);

        if (user.isPresent()) {
            // If user is found in DB, return it with HTTP 200 OK
            return ResponseEntity.ok(user.get());
        } else {
            // If user not found, return HTTP 404 Not Found
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<UserModel> createUser(@RequestBody UserModel user) {
        // Check if user already exists (optional, based on your logic)
        Optional<UserModel> existingUser = userRepository.findByEmail(user.getEmail());

        if (existingUser.isPresent()) {
            // Email already exists, return 409 Conflict
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } else {
            // Save new user and return 201 Created
            UserModel savedUser = userRepository.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
        }
    }

    // UPDATE User
    @PutMapping("/{id}")
    public ResponseEntity<UserModel> updateUser(@PathVariable String id, @RequestBody UserModel updatedUser) {
        Optional<UserModel> existingUserOpt = userRepository.findById(id);

        if (existingUserOpt.isPresent()) {
            UserModel existingUser = existingUserOpt.get();

            // Check if the new email is already used by another user
            Optional<UserModel> userWithSameEmail = userRepository.findByEmail(updatedUser.getEmail());
            if (userWithSameEmail.isPresent() && !userWithSameEmail.get().getId().equals(id)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();  // Email taken by another user
            }

            // Update fields
            existingUser.setName(updatedUser.getName());
            existingUser.setEmail(updatedUser.getEmail());
            // Update other fields if needed

            UserModel savedUser = userRepository.save(existingUser);
            return ResponseEntity.ok(savedUser);  // 200 OK
        } else {
            return ResponseEntity.notFound().build();  // 404 Not Found
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        Optional<UserModel> user = userRepository.findById(id);

        if (user.isPresent()) {
            userRepository.deleteById(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }



}
