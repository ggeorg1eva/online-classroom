package com.example.onlineclassroom.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "subjects")
@Getter
@Setter
public class Subject extends BaseEntity{
    @Column(nullable = false, unique = true)
    private String name;
}
