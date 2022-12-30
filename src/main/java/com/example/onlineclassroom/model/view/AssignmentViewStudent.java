package com.example.onlineclassroom.model.view;

import com.example.onlineclassroom.model.entity.SchoolClass;
import com.example.onlineclassroom.model.entity.Teacher;
import com.example.onlineclassroom.model.entity.enumeration.GradeEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
public class AssignmentViewStudent extends AssignmentView{
    private Long id;
    private String teacherFullName;
    private String subjectName;
    private GradeEnum grade;
}
