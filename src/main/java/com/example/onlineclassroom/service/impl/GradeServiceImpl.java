package com.example.onlineclassroom.service.impl;

import com.example.onlineclassroom.model.entity.Assignment;
import com.example.onlineclassroom.model.entity.Grade;
import com.example.onlineclassroom.model.entity.enumeration.GradeEnum;
import com.example.onlineclassroom.model.service.GradeServiceModel;
import com.example.onlineclassroom.repository.GradeRepository;
import com.example.onlineclassroom.service.GradeService;
import com.example.onlineclassroom.service.StudentService;
import com.example.onlineclassroom.service.SubjectService;
import com.example.onlineclassroom.service.TeacherService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GradeServiceImpl implements GradeService {
    private final GradeRepository gradeRepository;
    private final SubjectService subjectService;
    private final StudentService studentService;
    private final ModelMapper modelMapper;

    public GradeServiceImpl(GradeRepository gradeRepository, SubjectService subjectService, StudentService studentService, ModelMapper modelMapper) {
        this.gradeRepository = gradeRepository;
        this.subjectService = subjectService;
        this.studentService = studentService;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<GradeEnum> getAllGradesByStudent(Long studentId) {
        return gradeRepository.findAllByStudentId(studentId)
                .stream()
                .map(Grade::getGrade)
                .toList();
    }

    @Override
    public String getStudentGradesAsStringByStudId(Long studentId) {
        return this.getAllGradesByStudent(studentId)
                .stream().map(GradeEnum::name).collect(Collectors.joining(", "));
    }

    @Override
    public Long createGrade(GradeServiceModel serviceModel) {
        Grade grade = modelMapper.map(serviceModel, Grade.class);
        grade.setStudent(studentService.getStudentById(serviceModel.getStudentId()));
        grade.setSubject(subjectService.getSubjectByName(serviceModel.getSubjectName()));
        return gradeRepository.save(grade).getId();
    }

    @Override
    public GradeEnum getGradeEnumByAssignmentIdAndStudentEgn(Long assignmentId, String studentEgn) {
        Grade grade = gradeRepository.findByAssignmentIdAndStudentEgn(assignmentId, studentEgn).orElse(null);

        if (grade == null){
            return null;
        }
        return grade.getGrade();
    }

    @Override
    public void updateGradeWithAssignment(Long createdGradeId, Assignment assignment) {
        Grade grade = gradeRepository.findById(createdGradeId).orElse(null);
        grade.setAssignment(assignment);
        gradeRepository.save(grade);
    }

}
