package com.example.onlineclassroom.model.service;

import com.example.onlineclassroom.model.entity.enumeration.GradeEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class GradeServiceModel {
    private Long id;

    private GradeEnum grade;

    private LocalDateTime dateOfCreation;

    private String subjectName;

    private Long studentId;

    private String assignmentNameAndDueDate;
}
