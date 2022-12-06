package com.example.onlineclassroom.service.impl;

import com.example.onlineclassroom.model.entity.Assignment;
import com.example.onlineclassroom.model.entity.SchoolClass;
import com.example.onlineclassroom.model.entity.enumeration.ClassNameEnum;
import com.example.onlineclassroom.model.service.AssignmentServiceModel;
import com.example.onlineclassroom.model.view.AssignmentViewStudent;
import com.example.onlineclassroom.model.view.AssignmentViewTeacher;
import com.example.onlineclassroom.repository.AssignmentRepository;
import com.example.onlineclassroom.service.AssignmentService;
import com.example.onlineclassroom.service.GradeService;
import com.example.onlineclassroom.service.SchoolClassService;
import com.example.onlineclassroom.service.TeacherService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AssignmentServiceImpl implements AssignmentService {
    private final AssignmentRepository assignmentRepository;
    private final TeacherService teacherService;
    private final SchoolClassService schoolClassService;
    private final GradeService gradeService;
    private final ModelMapper modelMapper;

    public AssignmentServiceImpl(AssignmentRepository assignmentRepository, TeacherService teacherService, SchoolClassService schoolClassService, GradeService gradeService, ModelMapper modelMapper) {
        this.assignmentRepository = assignmentRepository;
        this.teacherService = teacherService;
        this.schoolClassService = schoolClassService;
        this.gradeService = gradeService;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<AssignmentViewTeacher> getAllAssignmentViewsByTeacherEgn(String principalEgn) {
        List<AssignmentViewTeacher> views = assignmentRepository.findAllByTeacherEgn(principalEgn)
                .stream()
                .map(assignment -> {
                    AssignmentViewTeacher view = modelMapper.map(assignment, AssignmentViewTeacher.class);
                    Set<ClassNameEnum> classNameEnums = assignment.getClasses().stream()
                            .map(SchoolClass::getName).collect(Collectors.toSet());

                    view.setClasses(classNameEnums);

                    view.setClassNamesAsString(String.join(", ", view.getClasses().stream().map(ClassNameEnum::name).collect(Collectors.toSet())));
                    return view;
                }).collect(Collectors.toList());

        return views;
    }

    @Override
    public void createAssignment(AssignmentServiceModel serviceModel) {
        Assignment assignment = modelMapper.map(serviceModel, Assignment.class);
        assignment.setTeacher(teacherService.getTeacherByEgn(serviceModel.getTeacherEgn()));
        assignment.setClasses(serviceModel.getClasses()
                .stream()
                .map(schoolClassService::getClassByName)
                .collect(Collectors.toSet()));

        assignmentRepository.save(assignment);

    }

    @Override
    public void deleteAssignmentById(Long id) {
        assignmentRepository.deleteById(id);
    }

    @Override
    public List<AssignmentViewStudent> getAllAssignmentViewsByTeacherId(Long teacherId, String studentEgn) {
        return assignmentRepository.findAllByTeacherId(teacherId)
                .stream()
                .map(assignment -> {
                    AssignmentViewStudent view = modelMapper.map(assignment, AssignmentViewStudent.class);
                    view.setTeacherFullName(assignment.getTeacher().getFirstName() + " " + assignment.getTeacher().getLastName());
                    view.setSubjectName(assignment.getTeacher().getSubject().getName());
                    view.setGrade(gradeService.getGradeEnumByAssignmentIdAndStudentEgn(assignment.getId(), studentEgn));
                    return view;
                })
                .sorted(Comparator.comparing(AssignmentViewStudent::getName)
                        .thenComparing(AssignmentViewStudent::getDueDate))
                .toList();
    }

    @Override
    public List<String> getAllAssignmentsNameAndDueDateByTeacherEgn(String principalEgn) {
       return assignmentRepository.findAllByTeacherEgn(principalEgn)
                .stream()
                .map(assignment -> {
                    return assignment.getName().concat(": " + assignment.getDueDate());
                }).toList();
    }

    @Override
    public void addAssignmentToGrade(String assignmentNameAndDueDateString, Long createdGradeId) {
        //regex for the ": " by which the name and the dueDate were concatenated
        String[] assignmentInfoArr = assignmentNameAndDueDateString.split(":\\s");

        String name = assignmentInfoArr[0];
        LocalDate dueDate = LocalDate.parse(assignmentInfoArr[1], DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        Assignment assignment = assignmentRepository.findByNameAndDueDate(name, dueDate).orElse(null);

        gradeService.updateGradeWithAssignment(createdGradeId, assignment);
    }
}
