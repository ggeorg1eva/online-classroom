package com.example.onlineclassroom.model.entity.enumeration;

public enum GradeEnum {
    POOR(2),
    MIDDLE(3),
    GOOD(4),
    VERY_GOOD(5),
    EXCELLENT(6);

    private final int gradeNumber;

    GradeEnum(int gradeNumber) {
        this.gradeNumber = gradeNumber;
    }

    public int getGradeNumber() {
        return gradeNumber;
    }
}
