package com.example.onlineclassroom.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Getter
@Setter
public class SchoolPerson extends BaseEntity {
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(nullable = false)
    private Integer age;

    @Column(nullable = false, unique = true)
    private String egn;

    @Column(name = "is_registered", columnDefinition = "boolean default false")
    private Boolean isRegistered;
}
