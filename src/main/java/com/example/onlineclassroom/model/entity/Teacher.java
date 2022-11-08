package com.example.onlineclassroom.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "teachers")
@Getter
@Setter
public class Teacher extends SchoolPerson{

    @ManyToMany
    private Set<SchoolClass> classes;

    @ManyToOne
    private Subject subject;

}
