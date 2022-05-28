package com.sirioitalia.api;

import com.sirioitalia.api.embeddable.Address;
import com.sirioitalia.api.model.Category;
import com.sirioitalia.api.model.Color;
import com.sirioitalia.api.model.Role;
import com.sirioitalia.api.model.User;
import com.sirioitalia.api.service.CategoryService;
import com.sirioitalia.api.service.ColorService;
import com.sirioitalia.api.service.RoleService;
import com.sirioitalia.api.service.UserService;
import com.sirioitalia.api.util.PBKDF2PasswordEncoder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;


@SpringBootApplication
public class ApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new PBKDF2PasswordEncoder();
    }

    CommandLineRunner run(UserService userService, RoleService roleService, ColorService colorService
            , CategoryService categoryService) {
        return args -> {
            roleService.createRole(new Role(null, "ROLE_CLIENT"));
            roleService.createRole(new Role(null, "ROLE_ADMIN"));

            userService.createUser(new User(null, "Thalke", "Ourabe",
                    "admin@gmail.com", "Azerty93",
                    null, LocalDate.of(1990, 01, 01),
                    "0603861010",
                    new Address("Victor Hugo", "Avenue", "93", "Paris", "75016"),
                    null, roleService.getRoleById(Integer.toUnsignedLong(1)), null, null));

            userService.createUser(new User(null, "Cahir", "Aep Dahy",
                    "cahir@gmail.com", "Azerty93",
                    null, LocalDate.of(1990, 01, 01),
                    "0634541510",
                    new Address("Victor Hugo", "Avenue", "93", "Paris", "75016"),
                    null, roleService.getRoleById(Integer.toUnsignedLong(1)), null, null));


            colorService.createColor(new Color(null, "Salmon", "#FA8072"));
            colorService.createColor(new Color(null, "Khaki", "#F0E68C"));
            colorService.createColor(new Color(null, "Indigo", "#4B0082"));

            categoryService.createCategory(new Category(null, "Lit"));
            categoryService.createCategory(new Category(null, "Armoire"));
            categoryService.createCategory(new Category(null, "Chaise et fauteuil"));
        };
    }

}