package com.example.onlineclassroom.service;

import com.example.onlineclassroom.model.entity.Teacher;
import com.example.onlineclassroom.model.view.SchoolClassView;

import java.util.List;

public interface TeacherService {

    boolean registerTeacher(String egn);

    Teacher getTeacherByEgn(String teacherEgn);

    List<SchoolClassView> getClassesByTeacherEgn(String principalEgn);

    String getTeacherSubjectName(String principalEgn);

    Long getTeacherIdBySchoolClassIdAndSubjectId(Long schoolClassId, Long subjectId);
}
