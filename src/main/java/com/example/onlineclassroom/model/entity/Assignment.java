package com.example.onlineclassroom.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Assignment that = (Assignment) o;
        return Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(creationDate, that.creationDate) && Objects.equals(dueDate, that.dueDate) && Objects.equals(teacher, that.teacher) && Objects.equals(classes, that.classes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, creationDate, dueDate, teacher, classes);
    }
}
