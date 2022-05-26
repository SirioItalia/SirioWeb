package com.sirioitalia.api.controller;


import com.sirioitalia.api.exception.ResourceException;
import com.sirioitalia.api.model.User;
import com.sirioitalia.api.projection.CartProjection;
import com.sirioitalia.api.projection.UserProjection;
import com.sirioitalia.api.repository.UserRepository;
import com.sirioitalia.api.service.CartService;
import com.sirioitalia.api.service.UserService;
import com.sirioitalia.api.util.PBKDF2PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final CartService cartService;
    private final PBKDF2PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository, UserService userService, PBKDF2PasswordEncoder passwordEncoder, CartService cartService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.cartService = cartService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public Iterable<UserProjection.Full> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{id}/carts")
    public Iterable<CartProjection> getItemsInUserCart(@PathVariable Long id) {
        return cartService.getItemsInCartByUserId(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserProjection.Full> getUserById(@PathVariable Long id) throws ResourceException {
        UserProjection.Full foundedUser = userService.getUserById(id);


        return new ResponseEntity<>(foundedUser, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long id) throws ResourceException {
        userService.deleteUser(id);


        return new ResponseEntity<>(HttpStatus.GONE);
    }

    @DeleteMapping("/{id}/carts")
    public ResponseEntity<HttpStatus> deleteItemsInUserCart(@PathVariable Long id) throws ResourceException {
        cartService.deleteAllCartByUserId(id);


        return new ResponseEntity<>(HttpStatus.GONE);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserProjection.Full> updateUser(
            @PathVariable Long id,
            @RequestBody User userDetails) throws ResourceException {

        UserProjection.Full updatedUser = userService.updateUser(id, userDetails);


        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<User> createUser(@Valid @RequestBody User userDetails) throws ResourceException {
        System.out.println("hash: " + userDetails.getPasswordHash());
        User createdUser = userService.createUser(userDetails);

        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }
}

