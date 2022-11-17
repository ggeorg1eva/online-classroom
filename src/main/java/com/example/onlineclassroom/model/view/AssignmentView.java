package com.example.onlineclassroom.model.view;

import com.example.onlineclassroom.model.entity.SchoolClass;
import com.example.onlineclassroom.model.entity.Teacher;
import com.example.onlineclassroom.model.entity.enumeration.ClassNameEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.util.Set;
@Getter
@Setter
public class AssignmentView {
    private Long id;

    private String name;

    private String description;

    private LocalDate creationDate;

    private LocalDate dueDate;

    private Set<ClassNameEnum> classes;

    private String classNamesAsString;
}
