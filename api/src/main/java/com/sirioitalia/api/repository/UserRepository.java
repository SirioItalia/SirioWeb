package com.sirioitalia.api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.sirioitalia.api.model.User;


@Repository
public interface UserRepository extends CrudRepository<User, Long> {

}
