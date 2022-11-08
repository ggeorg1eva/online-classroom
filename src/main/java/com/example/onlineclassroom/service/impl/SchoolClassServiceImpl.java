package com.example.onlineclassroom.service.impl;

import com.example.onlineclassroom.model.entity.SchoolClass;
import com.example.onlineclassroom.model.entity.enumeration.ClassNameEnum;
import com.example.onlineclassroom.repository.SchoolClassRepository;
import com.example.onlineclassroom.service.SchoolClassService;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class SchoolClassServiceImpl implements SchoolClassService {
    private final SchoolClassRepository classRepository;

    public SchoolClassServiceImpl(SchoolClassRepository classRepository) {
        this.classRepository = classRepository;
    }

    @Override
    public SchoolClass getClassByName(ClassNameEnum nameEnum) {
        return classRepository.findByName(nameEnum).orElse(null);
    }


}
