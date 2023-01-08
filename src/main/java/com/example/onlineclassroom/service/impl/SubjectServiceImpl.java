package com.example.onlineclassroom.service.impl;

import com.example.onlineclassroom.model.entity.Subject;
import com.example.onlineclassroom.repository.SubjectRepository;
import com.example.onlineclassroom.service.SubjectService;
import org.springframework.stereotype.Service;

@Service
public class SubjectServiceImpl implements SubjectService {
    private final SubjectRepository subjectRepository;

    public SubjectServiceImpl(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Override
    public Subject getSubjectByName(String name) {
        return subjectRepository.findByName(name).orElse(null);
    }

    @Override
    public String getSubjectNameById(Long id) {
        return subjectRepository.findById(id).orElse(null).getName();
    }

}
