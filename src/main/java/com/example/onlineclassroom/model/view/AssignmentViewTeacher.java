package com.example.onlineclassroom.model.view;

import com.example.onlineclassroom.model.entity.enumeration.ClassNameEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
public class AssignmentViewTeacher extends AssignmentView {
    private Set<ClassNameEnum> classes;

    private String classNamesAsString;
}
