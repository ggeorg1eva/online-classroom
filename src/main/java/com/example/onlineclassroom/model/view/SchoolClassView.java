package com.example.onlineclassroom.model.view;

import com.example.onlineclassroom.model.entity.enumeration.ClassNameEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SchoolClassView {
    private Long id;
    private ClassNameEnum name;
}
