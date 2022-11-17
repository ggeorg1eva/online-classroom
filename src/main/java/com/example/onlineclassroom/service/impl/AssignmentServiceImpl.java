package com.example.onlineclassroom.service.impl;

import com.example.onlineclassroom.model.entity.Assignment;
import com.example.onlineclassroom.model.entity.SchoolClass;
import com.example.onlineclassroom.model.entity.enumeration.ClassNameEnum;
import com.example.onlineclassroom.model.service.AssignmentServiceModel;
import com.example.onlineclassroom.model.view.AssignmentView;
import com.example.onlineclassroom.repository.AssignmentRepository;
import com.example.onlineclassroom.service.AssignmentService;
import com.example.onlineclassroom.service.SchoolClassService;
import com.example.onlineclassroom.service.TeacherService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AssignmentServiceImpl implements AssignmentService {
    private final AssignmentRepository assignmentRepository;
    private final TeacherService teacherService;
    private final SchoolClassService schoolClassService;
    private final ModelMapper modelMapper;

    public AssignmentServiceImpl(AssignmentRepository assignmentRepository, TeacherService teacherService, SchoolClassService schoolClassService, ModelMapper modelMapper) {
        this.assignmentRepository = assignmentRepository;
        this.teacherService = teacherService;
        this.schoolClassService = schoolClassService;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<AssignmentView> getAllAssignmentViewsByTeacherEgn(String principalEgn) {
        List<AssignmentView> views = assignmentRepository.findAllByTeacherEgn(principalEgn)
                .stream()
                .map(assignment -> {
                    AssignmentView view = modelMapper.map(assignment, AssignmentView.class);
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
}
