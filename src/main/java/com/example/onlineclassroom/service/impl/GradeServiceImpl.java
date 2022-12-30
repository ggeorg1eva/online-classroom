package com.example.onlineclassroom.service.impl;

import com.example.onlineclassroom.model.binding.GradeAddBindingModel;
import com.example.onlineclassroom.model.entity.Assignment;
import com.example.onlineclassroom.model.entity.Grade;
import com.example.onlineclassroom.model.entity.enumeration.GradeEnum;
import com.example.onlineclassroom.model.service.GradeServiceModel;
import com.example.onlineclassroom.model.view.GradeView;
import com.example.onlineclassroom.repository.GradeRepository;
import com.example.onlineclassroom.service.AssignmentService;
import com.example.onlineclassroom.service.GradeService;
import com.example.onlineclassroom.service.StudentService;
import com.example.onlineclassroom.service.SubjectService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GradeServiceImpl implements GradeService {
    private final GradeRepository gradeRepository;
    private final SubjectService subjectService;
    private final StudentService studentService;
    private final AssignmentService assignmentService;
    private final ModelMapper modelMapper;

    public GradeServiceImpl(GradeRepository gradeRepository, SubjectService subjectService, StudentService studentService, AssignmentService assignmentService, ModelMapper modelMapper) {
        this.gradeRepository = gradeRepository;
        this.subjectService = subjectService;
        this.studentService = studentService;
        this.assignmentService = assignmentService;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean createGrade(GradeServiceModel serviceModel) {
        Grade grade = modelMapper.map(serviceModel, Grade.class);
        grade.setStudent(studentService.getStudentById(serviceModel.getStudentId()));
        grade.setSubject(subjectService.getSubjectByName(serviceModel.getSubjectName()));

        if (serviceModel.getAssignmentNameAndDueDate() != null) {
            Assignment assignment = assignmentService.getAssignmentByNameAndDueDateStr(serviceModel.getAssignmentNameAndDueDate());

            boolean isExist = checkIfGradeAlreadyExists(assignment, serviceModel.getStudentId());
            if (isExist){
                return false;
            }else {
                grade.setAssignment(assignment);
            }
        }
        gradeRepository.save(grade);
        return true;
    }
    private boolean checkIfGradeAlreadyExists(Assignment assignment, Long studentId) {
        //we check in DB if the student with this Id already has a grade for this assignment
        Optional<Grade> gradeWithThisAssignment = gradeRepository.findByAssignmentAndStudentId(assignment, studentId);

        //if grade for the student with this assignment already exists, we cannot add another grade to it
        // so we have to terminate the creation of the grade ->
        if (gradeWithThisAssignment.isPresent()) {
            return true;
        }

        //if grade for the student with this id for this assignment doesnt exist in the DB
        return false;

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
    public List<GradeView> getGradeViewsByStudentIdAndSubjectId(Long studentId, Long subjectId) {
        //returns gradeViews sorted by creation date
        List<GradeView> views = gradeRepository.findAllByStudentIdAndSubjectId(studentId, subjectId)
                .stream()
                .sorted(Comparator.comparing(Grade::getDateOfCreation))
                .map(grade -> {
                    GradeView view = modelMapper.map(grade, GradeView.class);
                    view.setSubjectName(grade.getSubject().getName());
                    view.setStudentId(grade.getStudent().getId());
                    view.setDateOfCreation(grade.getDateOfCreation().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                    return view;
                })
                .toList();

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

    @Override
    public boolean findGradesByAssignmentId(Long id) {
        //if there are grades on this assignment, the method will return true
        return !gradeRepository.findAllByAssignmentId(id).isEmpty();
    }
}
