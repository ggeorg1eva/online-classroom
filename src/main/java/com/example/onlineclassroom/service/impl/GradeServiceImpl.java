package com.example.onlineclassroom.service.impl;

import com.example.onlineclassroom.model.binding.GradeAddBindingModel;
import com.example.onlineclassroom.model.entity.Assignment;
import com.example.onlineclassroom.model.entity.Grade;
import com.example.onlineclassroom.model.entity.enumeration.GradeEnum;
import com.example.onlineclassroom.model.service.GradeServiceModel;
import com.example.onlineclassroom.model.view.GradeView;
import com.example.onlineclassroom.repository.GradeRepository;
import com.example.onlineclassroom.service.GradeService;
import com.example.onlineclassroom.service.StudentService;
import com.example.onlineclassroom.service.SubjectService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
    public Long createGrade(GradeServiceModel serviceModel) {
        Grade grade = modelMapper.map(serviceModel, Grade.class);
        grade.setStudent(studentService.getStudentById(serviceModel.getStudentId()));
        grade.setSubject(subjectService.getSubjectByName(serviceModel.getSubjectName()));
        return gradeRepository.save(grade).getId();
    }

    @Override
    public GradeEnum getGradeEnumByAssignmentIdAndStudentEgn(Long assignmentId, String studentEgn) {
        Grade grade = gradeRepository.findByAssignmentIdAndStudentEgn(assignmentId, studentEgn).orElse(null);

        if (grade == null) {
            return null;
        }
        return grade.getGrade();
    }

    @Override
    public boolean updateGradeWithAssignment(Long createdGradeId, Assignment assignment, GradeAddBindingModel gradeAddBindingModel) {
        //this is the grade we are creating right now and to which we want to set the assignment
        Grade gradeToAddAssignment = gradeRepository.findById(createdGradeId).orElse(null);

        //we check in DB if the student with this Id already has a grade for this assignment
        Optional<Grade> gradeWithThisAssignment = gradeRepository.findByAssignmentAndStudentId(assignment, gradeAddBindingModel.getStudentId());

        //if grade for the student with this assignment already exists, we cannot add another grade to it
        // so we have to terminate the updating of the grade we are creating right now ->
        // it has already been created and in this method we are trying to update it but in this case we have to delete it altogether
        if (gradeWithThisAssignment.isPresent()) {
            gradeRepository.delete(gradeToAddAssignment);
            return false;
        }

        //if grade for the student with this id for this assignment doesnt exist in the DB
        //then we continue with our operation and we update the grade with the assignment
        gradeToAddAssignment.setAssignment(assignment);
        gradeRepository.save(gradeToAddAssignment);
        return true;

    }

    @Override
    public List<GradeView> getGradeViewsByStudentIdAndSubjectId(Long studentId, Long subjectId) {
        List<GradeView> views = gradeRepository.findAllByStudentIdAndSubjectId(studentId, subjectId)
                .stream()
                .map(grade -> {
                    GradeView view = modelMapper.map(grade, GradeView.class);
                    view.setSubjectName(grade.getSubject().getName());
                    view.setStudentId(grade.getStudent().getId());
                    return view;
                }).toList();

        return views;

    }

    @Override
    public String getGradesViewListAsString(List<GradeView> gradesBySubject) {
        return gradesBySubject
                .stream()
                .map(GradeView::getGrade)
                .map(GradeEnum::getGradeNumber)
                .map(Object::toString)
                .collect(Collectors.joining(", "));
    }
}
