package com.example.onlineclassroom.service;

import com.example.onlineclassroom.model.service.AssignmentServiceModel;
import com.example.onlineclassroom.model.view.AssignmentViewStudent;
import com.example.onlineclassroom.model.view.AssignmentViewTeacher;

import java.util.List;

public interface AssignmentService {
    List<AssignmentViewTeacher> getAllAssignmentViewsByTeacherEgn(String principalEgn);

    void createAssignment(AssignmentServiceModel serviceModel);

    void deleteAssignmentById(Long id);

    List<AssignmentViewStudent> getAllAssignmentViewsByTeacherId(Long teacherId, String studentEgn);

    List<String> getAllAssignmentsNameAndDueDateByTeacherEgn(String principalEgn);

    void addAssignmentToGrade(String assignmentNameAndDueDateString, Long createdGradeId);
}
