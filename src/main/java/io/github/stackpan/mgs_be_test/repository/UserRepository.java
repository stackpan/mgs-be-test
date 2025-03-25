package io.github.stackpan.mgs_be_test.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import io.github.stackpan.mgs_be_test.entity.User;

public interface UserRepository extends CrudRepository<User, UUID> {

    Optional<User> findByUsername(String username);

}
