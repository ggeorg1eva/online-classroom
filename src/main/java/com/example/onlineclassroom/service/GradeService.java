package com.example.onlineclassroom.service;

import com.example.onlineclassroom.model.binding.GradeAddBindingModel;
import com.example.onlineclassroom.model.entity.Assignment;
import com.example.onlineclassroom.model.entity.enumeration.GradeEnum;
import com.example.onlineclassroom.model.service.GradeServiceModel;
import com.example.onlineclassroom.model.view.GradeView;

import java.util.List;

public interface GradeService {

    boolean createGrade(GradeServiceModel serviceModel);

    GradeEnum getGradeEnumByAssignmentIdAndStudentEgn(Long assignmentId, String studentEgn);

    List<GradeView> getGradeViewsByStudentIdAndSubjectId(Long studentId, Long subjectId);

    String getGradesViewListAsString(List<GradeView> gradesBySubject);

    boolean findGradesByAssignmentId(Long id);
}
