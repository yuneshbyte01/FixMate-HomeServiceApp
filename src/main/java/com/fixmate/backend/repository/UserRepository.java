package com.fixmate.backend.repository;

import com.fixmate.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // Method to find a user by their email address
    Optional<User> findByEmail(String email);

    // Method to check if a user with the given email exists
    boolean existsByEmail(String email);

    String email(String email);
}
