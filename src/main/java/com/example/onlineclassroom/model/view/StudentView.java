package com.example.onlineclassroom.model.view;

import com.example.onlineclassroom.model.entity.enumeration.ClassNameEnum;
import com.example.onlineclassroom.model.entity.enumeration.GradeEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StudentView {
    private Long id;

    private String fullName;

    private String egn;

    private ClassNameEnum schoolClass;

    private String gradesAsString;
}
