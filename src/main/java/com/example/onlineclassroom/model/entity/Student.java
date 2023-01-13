package com.example.onlineclassroom.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "students")
@Getter
@Setter
public class Student extends SchoolPerson{

    @ManyToOne
    private SchoolClass schoolClass;
}
