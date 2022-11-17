package com.example.onlineclassroom.repository;

import com.example.onlineclassroom.model.entity.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    List<Assignment> findAllByTeacherEgn(String teacher_egn);
}
