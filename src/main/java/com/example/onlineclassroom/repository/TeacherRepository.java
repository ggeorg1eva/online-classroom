package com.example.onlineclassroom.repository;

import com.example.onlineclassroom.model.entity.SchoolClass;
import com.example.onlineclassroom.model.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Optional<Teacher> findByEgn(String egn);
    Optional<Teacher> findBySubjectIdAndClassesContaining(Long subject_id, SchoolClass classes);
}
