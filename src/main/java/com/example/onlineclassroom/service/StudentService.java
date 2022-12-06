package com.example.onlineclassroom.service;

import com.example.onlineclassroom.model.entity.Student;
import com.example.onlineclassroom.model.view.StudentView;

import java.util.List;

public interface StudentService {

    boolean registerStudent(String egn);

    List<StudentView> getStudentsByClassId(Long id);

    Student getStudentById(Long id);

    Long getSchoolClassIdByStudentEgn(String studentEgn);
}
