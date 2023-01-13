package com.example.onlineclassroom.model.view;

import com.example.onlineclassroom.model.entity.enumeration.GradeEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GradeView {
    private GradeEnum grade;

    private String dateOfCreation;

    private String subjectName;

    private Long studentId;

}
