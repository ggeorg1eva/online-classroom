package com.example.onlineclassroom.model.entity;

import com.example.onlineclassroom.model.entity.enumeration.ClassNameEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "classes")
@Getter
@Setter
public class SchoolClass extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private ClassNameEnum name;

    @ManyToMany
    private Set<Subject> subjects;

    @OneToMany(mappedBy = "schoolClass")
    private Set<Student> students;

}
