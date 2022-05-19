package com.sirioitalia.api.service;

import com.sirioitalia.api.exception.ResourceException;
import com.sirioitalia.api.model.Role;
import com.sirioitalia.api.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleService {
    private RoleRepository roleRepository;

    public RoleService() {
        super();
    }

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        super();

        this.roleRepository = roleRepository;
    }

    public Iterable<Role> getRoles() {
        return roleRepository.findAll();
    }

    public Role getRoleById(Long roleId) throws ResourceException {
        Role foundedRole = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceException("FindRoleFailed", HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND));

        return foundedRole;
    }

    @Transactional
    public Role createRole(Role roleDetails) throws ResourceException {
        try {
            return roleRepository.save(roleDetails);
        } catch (Exception e) {
            throw new ResourceException(e.getMessage(), e.getCause(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public void deleteRole(Long roleId) throws ResourceException {
        try {
            Role roleToDelete = roleRepository.findById(roleId)
                    .orElseThrow(() -> new ResourceException("FindRoleFailed",
                            HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND));

            roleRepository.delete(roleToDelete);
        } catch (Exception e) {
            throw new ResourceException(e.getMessage(), e.getCause(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
