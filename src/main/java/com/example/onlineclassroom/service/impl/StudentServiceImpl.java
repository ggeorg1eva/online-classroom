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

    @Override
    public boolean registerStudent(String egn) {
        Student student = studentRepository.findByEgn(egn).orElse(null);
        if (student == null){
            return false;
        }

        student.setIsRegistered(true);
        studentRepository.save(student);

        return true;
    }
}
