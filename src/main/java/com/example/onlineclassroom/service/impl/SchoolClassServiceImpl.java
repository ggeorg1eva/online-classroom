package com.example.onlineclassroom.service.impl;

import com.example.onlineclassroom.model.entity.SchoolClass;
import com.example.onlineclassroom.model.entity.enumeration.ClassNameEnum;
import com.example.onlineclassroom.model.view.SchoolClassView;
import com.example.onlineclassroom.model.view.SubjectView;
import com.example.onlineclassroom.repository.SchoolClassRepository;
import com.example.onlineclassroom.service.SchoolClassService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class SchoolClassServiceImpl implements SchoolClassService {
    private final SchoolClassRepository schoolClassRepository;
    private final ModelMapper modelMapper;


    public SchoolClassServiceImpl(SchoolClassRepository schoolClassRepository, ModelMapper modelMapper) {
        this.schoolClassRepository = schoolClassRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public SchoolClass getClassByName(ClassNameEnum nameEnum) {
        return schoolClassRepository.findByName(nameEnum).orElse(null);
    }

    @Override
    public SchoolClassView mapEntityToView(SchoolClass schoolClass) {
        return modelMapper.map(schoolClass, SchoolClassView.class);
    }

    @Override
    public List<SubjectView> getSubjectsBySchoolClassId(Long schoolClassId) {
        SchoolClass schoolClass = schoolClassRepository.findById(schoolClassId).orElse(null);
        List<SubjectView> subjects = schoolClass.getSubjects()
                .stream()
                .map(subject -> modelMapper.map(subject, SubjectView.class))
                .sorted(Comparator.comparing(SubjectView::getName))
                .toList();
        return subjects;
    }

    @Override
    public SchoolClass getClassById(Long schoolClassId) {
        return schoolClassRepository.findById(schoolClassId).orElse(null);
    }


}
