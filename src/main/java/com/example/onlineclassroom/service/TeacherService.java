package com.example.onlineclassroom.service;

import com.example.onlineclassroom.model.entity.Teacher;
import com.example.onlineclassroom.model.view.SchoolClassView;
import com.example.onlineclassroom.model.view.SubjectView;

import java.util.List;

public interface TeacherService {

    boolean registerTeacher(String egn);

    Teacher getTeacherByEgn(String teacherEgn);

    List<SchoolClassView> getClassesByTeacherEgn(String principalEgn);

    SubjectView getTeacherSubjectViewByEgn(String principalEgn);

    Long getTeacherIdBySchoolClassIdAndSubjectId(Long schoolClassId, Long subjectId);
}
