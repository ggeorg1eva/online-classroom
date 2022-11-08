package com.example.onlineclassroom.service;

import com.example.onlineclassroom.model.entity.SchoolClass;
import com.example.onlineclassroom.model.entity.enumeration.ClassNameEnum;

public interface SchoolClassService {
    SchoolClass getClassByName(ClassNameEnum nameEnum);
}
