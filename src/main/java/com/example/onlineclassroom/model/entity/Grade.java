package com.example.onlineclassroom.model.entity;

import com.example.onlineclassroom.model.entity.enumeration.GradeEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "grades")
@Getter
@Setter
public class Grade extends BaseEntity{
    @Enumerated(EnumType.STRING)
    private GradeEnum grade;

    @ManyToOne
    private Subject subject;

    @ManyToOne
    private Student student;
}
