package com.sirioitalia.api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.sirioitalia.api.model.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

}
