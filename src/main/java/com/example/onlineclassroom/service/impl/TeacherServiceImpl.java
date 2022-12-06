package com.example.onlineclassroom.service.impl;

import com.example.onlineclassroom.model.entity.SchoolClass;
import com.example.onlineclassroom.model.entity.Teacher;
import com.example.onlineclassroom.model.view.SchoolClassView;
import com.example.onlineclassroom.repository.TeacherRepository;
import com.example.onlineclassroom.service.SchoolClassService;
import com.example.onlineclassroom.service.SubjectService;
import com.example.onlineclassroom.service.TeacherService;
import com.example.onlineclassroom.service.UserRoleService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
        if (teacher == null) {
            return false;
        }
        teacher.setIsRegistered(true);

        teacherRepository.save(teacher);

        return true;
    }

    @Override
    public Teacher getTeacherByEgn(String teacherEgn) {
        return teacherRepository.findByEgn(teacherEgn).orElse(null);
    }

    @Override
    public List<SchoolClassView> getClassesByTeacherEgn(String principalEgn) {
        List<SchoolClassView> classes = Objects.requireNonNull(teacherRepository.findByEgn(principalEgn).orElse(null))
                .getClasses()
                .stream()
                .map(schoolClassService::mapEntityToView)
                .collect(Collectors.toList());
        classes.sort(Comparator.comparing(SchoolClassView::getName));
        return classes;
    }

    @Override
    public String getTeacherSubjectName(String principalEgn) {
        return this.getTeacherByEgn(principalEgn).getSubject().getName();
    }

    @Override
    public Long getTeacherIdBySchoolClassIdAndSubjectId(Long schoolClassId, Long subjectId) {
        SchoolClass schoolClass = schoolClassService.getClassById(schoolClassId);
        return Objects.requireNonNull(teacherRepository.findBySubjectIdAndClassesContaining(subjectId, schoolClass).orElse(null)).getId();
    }

}
