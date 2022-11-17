package com.example.onlineclassroom.model.binding;

import com.example.onlineclassroom.model.entity.enumeration.ClassNameEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Set;
@Getter
@Setter
public class AssignmentCreateBindingModel {
    @NotBlank
    @Size(min = 3, max = 20)
    private String name;

    @Size(max = 200)
    private String description;

    private LocalDate creationDate;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Future
    private LocalDate dueDate;

    private String teacherName;

    @NotEmpty
    private Set<ClassNameEnum> classes;
}
