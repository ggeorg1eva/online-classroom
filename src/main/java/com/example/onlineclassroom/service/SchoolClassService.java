package com.example.onlineclassroom.service;

import com.example.onlineclassroom.model.entity.SchoolClass;
import com.example.onlineclassroom.model.entity.enumeration.ClassNameEnum;
import com.example.onlineclassroom.model.view.SchoolClassView;
import com.example.onlineclassroom.model.view.SubjectView;

import java.util.List;

public interface SchoolClassService {
    SchoolClass getClassByName(ClassNameEnum nameEnum);

    SchoolClassView mapEntityToView(SchoolClass schoolClass);

    List<SubjectView> getSubjectsBySchoolClassId(Long schoolClassId);

    SchoolClass getClassById(Long schoolClassId);
}
