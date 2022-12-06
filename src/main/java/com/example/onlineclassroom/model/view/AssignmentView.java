package com.example.onlineclassroom.model.view;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public abstract class AssignmentView {
    private Long id;

    private String name;

    private String description;

    private LocalDate creationDate;

    private LocalDate dueDate;
}
