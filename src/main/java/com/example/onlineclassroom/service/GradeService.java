package com.example.onlineclassroom.service;

import com.example.onlineclassroom.model.entity.Assignment;
import com.example.onlineclassroom.model.entity.enumeration.GradeEnum;
import com.example.onlineclassroom.model.service.GradeServiceModel;

import java.util.List;

public interface GradeService {
    List<GradeEnum> getAllGradesByStudent(Long studentId);

    String getStudentGradesAsStringByStudId(Long id);

    Long createGrade(GradeServiceModel serviceModel);

    GradeEnum getGradeEnumByAssignmentIdAndStudentEgn(Long assignmentId, String studentEgn);

    void updateGradeWithAssignment(Long createdGradeId, Assignment assignment);
}
