package com.example.onlineclassroom.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "assignments")
@Getter
@Setter
public class Assignment extends BaseEntity{
    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "text")
    private String description;

    @Column(name = "date_of_creation", nullable = false)
    private LocalDate creationDate;

    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;

    @ManyToOne
    private Teacher teacher;

    @ManyToMany
    private Set<SchoolClass> classes;
}
