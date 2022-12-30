package com.example.onlineclassroom.model.view;

import com.example.onlineclassroom.model.entity.Assignment;
import com.example.onlineclassroom.model.entity.Student;
import com.example.onlineclassroom.model.entity.Subject;
import com.example.onlineclassroom.model.entity.enumeration.GradeEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

@Getter
@Setter
public class GradeView {
    private GradeEnum grade;

    private String dateOfCreation;

    private String subjectName;

    private Long studentId;

}
