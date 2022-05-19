package com.sirioitalia.api.controller;


import com.sirioitalia.api.exception.ResourceException;
import com.sirioitalia.api.model.User;
import com.sirioitalia.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {
    private UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) throws ResourceException {
        User foundedUser = userService.getUserById(id);


        return new ResponseEntity<>(foundedUser, HttpStatus.FOUND);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);


        return new ResponseEntity<>(HttpStatus.GONE);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(
            @PathVariable Long id,
            @RequestBody User userDetails) throws ResourceException {

        User updatedUser = userService.updateUser(id, userDetails);


        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<User> createUser(@Valid @RequestBody User userDetails) throws ResourceException {
        User createdUser = userService.createUser(userDetails);

        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

}

