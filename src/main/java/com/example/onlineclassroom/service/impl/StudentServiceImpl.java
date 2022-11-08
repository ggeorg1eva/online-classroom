package com.example.onlineclassroom.service.impl;

import com.example.onlineclassroom.model.entity.Student;
import com.example.onlineclassroom.model.entity.enumeration.ClassNameEnum;
import com.example.onlineclassroom.repository.StudentRepository;
import com.example.onlineclassroom.service.SchoolClassService;
import com.example.onlineclassroom.service.StudentService;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final SchoolClassService schoolClassService;

    public StudentServiceImpl(StudentRepository studentRepository, SchoolClassService schoolClassService) {
        this.studentRepository = studentRepository;
        this.schoolClassService = schoolClassService;
    }

}
