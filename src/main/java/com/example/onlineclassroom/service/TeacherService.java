package com.example.onlineclassroom.service;

import com.example.onlineclassroom.model.entity.Teacher;

public interface TeacherService {

    boolean registerTeacher(String egn);

    Teacher getTeacherByEgn(String teacherEgn);
}
