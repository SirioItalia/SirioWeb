package com.sirioitalia.api.controller;

import com.sirioitalia.api.exception.ResourceException;
import com.sirioitalia.api.model.Role;
import com.sirioitalia.api.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class RoleController {
    private RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/roles")
    public Iterable<Role> getRoles () {
        return roleService.getRoles();
    }

    @GetMapping("/roles/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable Long id) throws ResourceException {
        Role foundedRole = roleService.getRoleById(id);


        return new ResponseEntity<>(foundedRole, HttpStatus.FOUND);
    }

    @PostMapping("/roles")
    public ResponseEntity<Role> createRole(@Valid @RequestBody Role roleDetails) throws ResourceException {
        Role createdRole = roleService.createRole(roleDetails);


        return new ResponseEntity<>(createdRole, HttpStatus.CREATED);
    }

    @DeleteMapping("/roles/{id}")
    public ResponseEntity<HttpStatus> deleteRole(@PathVariable Long id) throws ResourceException {
        roleService.deleteRole(id);


        return new ResponseEntity<>(HttpStatus.GONE);
    }

}
