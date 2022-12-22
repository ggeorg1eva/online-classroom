package com.example.onlineclassroom.service.impl;

import com.example.onlineclassroom.model.entity.Student;
import com.example.onlineclassroom.model.entity.enumeration.ClassNameEnum;
import com.example.onlineclassroom.model.entity.enumeration.GradeEnum;
import com.example.onlineclassroom.model.view.StudentView;
import com.example.onlineclassroom.repository.StudentRepository;
import com.example.onlineclassroom.service.GradeService;
import com.example.onlineclassroom.service.SchoolClassService;
import com.example.onlineclassroom.service.StudentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final SchoolClassService schoolClassService;
    private final ModelMapper modelMapper;

    public StudentServiceImpl(StudentRepository studentRepository, SchoolClassService schoolClassService,ModelMapper modelMapper) {
        this.studentRepository = studentRepository;
        this.schoolClassService = schoolClassService;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean registerStudent(String egn) {
        Student student = studentRepository.findByEgn(egn).orElse(null);
        if (student == null) {
            return false;
        }

        student.setIsRegistered(true);
        studentRepository.save(student);

        return true;
    }

    @Override
    public List<StudentView> getStudentsByClassId(Long id) {
        return studentRepository.findAllBySchoolClassId(id)
                .stream()
                .map(student -> {
                    StudentView view = modelMapper.map(student, StudentView.class);
                    view.setFullName(student.getFirstName() + " " + student.getLastName());
                    view.setSchoolClass(student.getSchoolClass().getName());
                    return view;
                })
                .toList();
    }

    @Override
    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    @Override
    public Long getSchoolClassIdByStudentEgn(String studentEgn) {
        Student student = studentRepository.findByEgn(studentEgn).orElse(null);

        return student.getSchoolClass().getId();
    }

    @Override
    public Long getStudentIdByEgn(String egn) {
        return Objects.requireNonNull(studentRepository
                        .findByEgn(egn)
                        .orElse(null))
                .getId();
    }
}
