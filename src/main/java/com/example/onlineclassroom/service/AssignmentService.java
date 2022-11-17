package com.example.onlineclassroom.service;

import com.example.onlineclassroom.model.service.AssignmentServiceModel;
import com.example.onlineclassroom.model.view.AssignmentView;

import java.util.List;

public interface AssignmentService {
    List<AssignmentView> getAllAssignmentViewsByTeacherEgn(String principalEgn);

    void createAssignment(AssignmentServiceModel serviceModel);

    void deleteAssignmentById(Long id);
}
