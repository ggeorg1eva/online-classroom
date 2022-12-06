package com.example.onlineclassroom.model.binding;

import com.example.onlineclassroom.model.entity.enumeration.GradeEnum;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
public class GradeAddBindingModel {
    @NotNull
    private GradeEnum grade;

    private LocalDateTime dateOfCreation;

    private String subjectName;

    private Long studentId;

    private String assignmentNameAndDueDateString;
}
