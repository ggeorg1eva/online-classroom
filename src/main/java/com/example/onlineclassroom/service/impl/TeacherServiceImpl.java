package com.example.onlineclassroom.service.impl;

import com.example.onlineclassroom.model.entity.SchoolClass;
import com.example.onlineclassroom.model.entity.Teacher;
import com.example.onlineclassroom.model.entity.enumeration.ClassNameEnum;
import com.example.onlineclassroom.model.view.SchoolClassView;
import com.example.onlineclassroom.model.view.SubjectView;
import com.example.onlineclassroom.model.view.TeacherProfileView;
import com.example.onlineclassroom.model.view.UserProfileView;
import com.example.onlineclassroom.repository.TeacherRepository;
import com.example.onlineclassroom.service.SchoolClassService;
import com.example.onlineclassroom.service.SubjectService;
import com.example.onlineclassroom.service.TeacherService;
import com.example.onlineclassroom.service.UserRoleService;
import org.modelmapper.ModelMapper;
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
    private final ModelMapper modelMapper;

    public TeacherServiceImpl(TeacherRepository teacherRepository, UserRoleService userRoleService, SubjectService subjectService, SchoolClassService schoolClassService, ModelMapper modelMapper) {
        this.teacherRepository = teacherRepository;
        this.userRoleService = userRoleService;
        this.subjectService = subjectService;
        this.schoolClassService = schoolClassService;
        this.modelMapper = modelMapper;
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
    public SubjectView getTeacherSubjectViewByEgn(String principalEgn) {
        return modelMapper.map(this.getTeacherByEgn(principalEgn).getSubject(), SubjectView.class);
    }

    @Override
    public Long getTeacherIdBySchoolClassIdAndSubjectId(Long schoolClassId, Long subjectId) {
        SchoolClass schoolClass = schoolClassService.getClassById(schoolClassId);
        return Objects.requireNonNull(teacherRepository.findBySubjectIdAndClassesContaining(subjectId, schoolClass).orElse(null)).getId();
    }

    @Override
    public TeacherProfileView getTeacherProfileInfoFromUserView(UserProfileView userView) {
        Teacher teacher = teacherRepository.findByEgn(userView.getEgn()).orElse(null);
        TeacherProfileView teacherView = modelMapper.map(teacher, TeacherProfileView.class);
        teacherView.setRole(userView.getRole());
        teacherView.setUsername(userView.getUsername());
        teacherView.setEmail(userView.getEmail());
        teacherView.setClassNamesAsString(getTeacherClassesAsString(teacher));
        return teacherView;
    }

    private String getTeacherClassesAsString(Teacher teacher) {
        return String.join(", ",
                teacher.getClasses()
                        .stream()
                        .map(SchoolClass::getName)
                        .map(ClassNameEnum::name)
                        .map(name -> name.replace('_', ' '))
                        .collect(Collectors.toSet()));
    }
}
