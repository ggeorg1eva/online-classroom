package com.example.onlineclassroom.repository;

import com.example.onlineclassroom.model.entity.SchoolClass;
import com.example.onlineclassroom.model.entity.enumeration.ClassNameEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SchoolClassRepository extends JpaRepository<SchoolClass, Long> {
    Optional<SchoolClass> findByName(ClassNameEnum name);
}
