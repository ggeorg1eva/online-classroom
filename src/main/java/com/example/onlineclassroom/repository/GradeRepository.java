package com.example.onlineclassroom.repository;

import com.example.onlineclassroom.model.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {
    List<Grade> findAllByStudentId(Long student_id);
    Optional<Grade> findByDateOfCreation(LocalDateTime dateOfCreation);
    Optional<Grade> findByAssignmentIdAndStudentEgn(Long assignment_id, String student_egn);
}
