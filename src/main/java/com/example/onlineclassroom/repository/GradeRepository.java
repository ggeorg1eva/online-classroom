package com.example.onlineclassroom.repository;

import com.example.onlineclassroom.model.entity.Assignment;
import com.example.onlineclassroom.model.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {
    List<Grade> findAllByStudentIdAndSubjectId(Long student_id, Long subject_id);
    Optional<Grade> findByAssignmentIdAndStudentEgn(Long assignment_id, String student_egn);
    Optional<Grade> findByAssignmentAndStudentId(Assignment assignment, Long student_id);
    List<Grade> findAllByAssignmentId(Long assignment_id);
}
