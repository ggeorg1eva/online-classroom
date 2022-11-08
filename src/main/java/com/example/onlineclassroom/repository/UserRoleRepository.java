package com.example.onlineclassroom.repository;

import com.example.onlineclassroom.model.entity.UserRole;
import com.example.onlineclassroom.model.entity.enumeration.UserRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    Optional<UserRole> findByRole(UserRoleEnum role);

}
