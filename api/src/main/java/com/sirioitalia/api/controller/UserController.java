package com.sirioitalia.api.controller;


import com.sirioitalia.api.exception.ResourceException;
import com.sirioitalia.api.model.User;
import com.sirioitalia.api.projection.UserProjection;
import com.sirioitalia.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public Iterable<UserProjection.Full> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserProjection.Full> getUserById(@PathVariable Long id) throws ResourceException {
        UserProjection.Full foundedUser = userService.getUserById(id);


        return new ResponseEntity<>(foundedUser, HttpStatus.FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);


        return new ResponseEntity<>(HttpStatus.GONE);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(
            @PathVariable Long id,
            @RequestBody User userDetails) throws ResourceException {

        User updatedUser = userService.updateUser(id, userDetails);


        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<User> createUser(@Valid @RequestBody User userDetails) throws ResourceException {
        System.out.println("hash: " + userDetails.getPasswordHash());
        User createdUser = userService.createUser(userDetails);

        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

}

