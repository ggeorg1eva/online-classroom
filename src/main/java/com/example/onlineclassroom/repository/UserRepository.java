package com.example.onlineclassroom.repository;

import com.example.onlineclassroom.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEgn(String egn);
    Optional<User> findByUsernameOrEmailOrEgn(String username, String email, String egn);
}
