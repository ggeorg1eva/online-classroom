package com.example.onlineclassroom.service.impl;

import com.example.onlineclassroom.model.entity.Teacher;
import com.example.onlineclassroom.model.entity.enumeration.ClassNameEnum;
import com.example.onlineclassroom.repository.TeacherRepository;
import com.example.onlineclassroom.service.SchoolClassService;
import com.example.onlineclassroom.service.SubjectService;
import com.example.onlineclassroom.service.TeacherService;
import com.example.onlineclassroom.service.UserRoleService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class TeacherServiceImpl implements TeacherService {
    private final TeacherRepository teacherRepository;
    private final UserRoleService userRoleService;
    private final SubjectService subjectService;
    private final SchoolClassService schoolClassService;

    public TeacherServiceImpl(TeacherRepository teacherRepository, UserRoleService userRoleService, SubjectService subjectService, SchoolClassService schoolClassService) {
        this.teacherRepository = teacherRepository;
        this.userRoleService = userRoleService;
        this.subjectService = subjectService;
        this.schoolClassService = schoolClassService;
    }


    @Override
    public boolean registerTeacher(String egn) {
        Teacher teacher = teacherRepository.findByEgn(egn).orElse(null);
        if (teacher == null){
            return false;
        }
        teacher.setIsRegistered(true);

        teacherRepository.save(teacher);

        return true;
    }
}
