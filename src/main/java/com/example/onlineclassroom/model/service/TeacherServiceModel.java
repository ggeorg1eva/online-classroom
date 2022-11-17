package com.example.onlineclassroom.model.service;
import com.example.onlineclassroom.model.entity.enumeration.ClassNameEnum;
import lombok.Getter;
import lombok.Setter;
import java.util.Set;

@Getter
@Setter
public class TeacherServiceModel {

    private String name;

    private String egn;

    private Set<ClassNameEnum> classes;

    private String subject;
}
