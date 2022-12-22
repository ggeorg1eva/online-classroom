package com.example.onlineclassroom.model.view;

import com.example.onlineclassroom.model.entity.enumeration.ClassNameEnum;
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

    //student view only keeps grades for a current subject because grades are always displayed by subjects
    private List<GradeView> gradesBySubject;

    private String gradesAsString;
}
