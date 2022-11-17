package com.example.onlineclassroom.model.service;

import com.example.onlineclassroom.model.entity.enumeration.ClassNameEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
public class AssignmentServiceModel {
    private String name;

    private String description;

    private LocalDate creationDate;

    private LocalDate dueDate;

    private String teacherEgn;

    private Set<ClassNameEnum> classes;
}
