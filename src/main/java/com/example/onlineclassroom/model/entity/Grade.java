package com.example.onlineclassroom.model.entity;

import com.example.onlineclassroom.model.entity.enumeration.GradeEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "grades")
@Getter
@Setter
public class Grade extends BaseEntity{
    @Enumerated(EnumType.STRING)
    private GradeEnum grade;

    @Column(name = "date_of_creation", nullable = false)
    private LocalDateTime dateOfCreation;

    @ManyToOne
    private Subject subject;

    @ManyToOne
    private Student student;

    @OneToOne(cascade = CascadeType.ALL)
    private Assignment assignment;

}
