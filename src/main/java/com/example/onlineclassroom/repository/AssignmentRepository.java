package com.example.onlineclassroom.repository;

import com.example.onlineclassroom.model.entity.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    List<Assignment> findAllByTeacherEgn(String teacher_egn);
    List<Assignment> findAllByTeacherId(Long teacher_id);
    Optional<Assignment> findByNameAndDueDate(String name, LocalDate dueDate);
}
