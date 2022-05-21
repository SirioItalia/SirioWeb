package com.sirioitalia.api.repository;

import com.sirioitalia.api.model.User;
import com.sirioitalia.api.projection.UserProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    @Query
    Iterable<UserProjection.Full> findBy();

    @Query
    Optional<UserProjection.Full> findProjectionById(Long userId);
}
