package io.github.stackpan.mgs_be_test.repository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import io.github.stackpan.mgs_be_test.entity.User;

public interface UserRepository extends CrudRepository<User, UUID> {

    Optional<User> findByUsername(String username);

    @Query("select u from User u where u.username = ?1 or u.email = ?2")
    Collection<User> findByUsernameOrEmail(String username, String email);

}
