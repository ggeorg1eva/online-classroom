package com.example.onlineclassroom.model.view;

import com.example.onlineclassroom.model.entity.enumeration.GradeEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssignmentViewStudent extends AssignmentView{
    private Long id;
    private String teacherFullName;
    private String subjectName;
    private GradeEnum grade;
}
