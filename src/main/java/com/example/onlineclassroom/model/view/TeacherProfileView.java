package com.example.onlineclassroom.model.view;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TeacherProfileView extends UserProfileView {
    private String subjectName;
    private String classNamesAsString;
}
