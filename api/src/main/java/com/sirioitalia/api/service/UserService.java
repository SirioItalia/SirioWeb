package com.sirioitalia.api.service;

import com.sirioitalia.api.exception.ResourceException;
import com.sirioitalia.api.model.User;
import com.sirioitalia.api.repository.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Data
@Service
public class UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }


    public User getUserById(Long itemId) throws ResourceException {
        User user = userRepository.findById(itemId)
                .orElseThrow(() -> new ResourceException("FindUserFailed", HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND));

        return user;
    }

    public List<User> getUsers() {
        return (List<User>) userRepository.findAll();
    }

    @Transactional
    public void deleteUser(Long userId) {
        User userToDelete = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceException(HttpStatus.NOT_FOUND.getReasonPhrase(), "User not found"));


        userRepository.delete(userToDelete);
    }

    @Transactional
    public User updateUser(Long userId, User userDetails) throws ResourceException {
        try {
            User foundedUser = userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceException(HttpStatus.NOT_FOUND.getReasonPhrase(), "User not found"));

            foundedUser.setEmail(userDetails.getEmail() == null
                    ? foundedUser.getEmail()
                    : userDetails.getEmail());

            foundedUser.setFirstName(userDetails.getFirstName() == null
                    ? foundedUser.getFirstName()
                    : userDetails.getFirstName());

            foundedUser.setLastName(userDetails.getLastName() == null
                    ? foundedUser.getLastName()
                    : userDetails.getLastName());

            if (userDetails.getPasswordHash() != null) {
                String formattedPassword = String.format("%s:%s", foundedUser.getPasswordHash(), foundedUser.getPasswordSalt());

                if (!passwordEncoder.matches(userDetails.getPasswordHash(), formattedPassword)) {
                    HashMap<String, String> hashedPassword = encodePassword(userDetails.getPasswordHash());
                    foundedUser.setPasswordHash(hashedPassword.get("hash"));
                    foundedUser.setPasswordSalt(hashedPassword.get("salt"));
                }
            }

            if (userDetails.getAddress() != null) {
                foundedUser.getAddress().setCity(userDetails.getAddress().getCity() == null
                        ? foundedUser.getAddress().getCity()
                        : userDetails.getAddress().getCity());

                foundedUser.getAddress().setStreetName(userDetails.getAddress().getStreetName() == null
                        ? foundedUser.getAddress().getStreetName()
                        : userDetails.getAddress().getStreetName());

                foundedUser.getAddress().setStreetNumber(userDetails.getAddress().getStreetNumber() == null
                        ? foundedUser.getAddress().getStreetNumber()
                        : userDetails.getAddress().getStreetNumber());

                foundedUser.getAddress().setZipCode(userDetails.getAddress().getZipCode() == null
                        ? foundedUser.getAddress().getZipCode()
                        : userDetails.getAddress().getZipCode());

            }

            foundedUser.setPhoneNumber(userDetails.getPhoneNumber() == null
                    ? foundedUser.getPhoneNumber()
                    : userDetails.getPhoneNumber());

            return userRepository.save(foundedUser);
        } catch (Exception e) {
            throw new ResourceException(e.getMessage(), e.getCause(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public User createUser(User userDetails) throws ResourceException {
        try {
            HashMap<String, String> hashedPassword = encodePassword(userDetails.getPasswordHash());

            userDetails.setPasswordHash(hashedPassword.get("hash"));
            userDetails.setPasswordSalt(hashedPassword.get("salt"));


            return userRepository.save(userDetails);
        } catch (Exception e) {
            throw new ResourceException(e.getMessage(), e.getCause(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private HashMap<String, String> encodePassword(CharSequence password) {
        HashMap<String, String> hashedPassword = new HashMap<>();

        String[] hashAndSaltPassword = passwordEncoder.encode(password).split(":");

        hashedPassword.put("hash", hashAndSaltPassword[0]);
        hashedPassword.put("salt", hashAndSaltPassword[1]);


        return hashedPassword;
    }

}