package com.adv;

import java.sql.Timestamp;

public class CourseGradeDetail {

    private final String courseName;
    private final String studentId;
    private final Float gradeValue; // Nutzen der Wrapper - Klasse, um NULL auch verarbeiten zu können
    private final String gradeDescription;
    private final String gradeType;
    private final Float weight;
    private final Timestamp createdAt;

    public CourseGradeDetail(String courseName,
                             String studentId,
                              Float gradeValue,
                              String gradeDescription,
                              String gradeType,
                              Float weight,
                              Timestamp createdAt) {

        this.courseName = courseName;
        this.studentId = studentId;
        this.gradeValue = gradeValue;
        this.gradeDescription = gradeDescription;
        this.gradeType = gradeType;
        this.weight = weight;
        this.createdAt = createdAt;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getStudentId() {
        return studentId;
    }

    public Float getGradeValue() {
        return gradeValue;
    }

    public String getGradeDescription() {
        return gradeDescription;
    }

    public String getGradeType() {
        return gradeType;
    }

    public Float getWeight() {
        return weight;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public StudentGradeDetail toStudentGradeDetail(String courseId) {
        return new StudentGradeDetail(
                courseId,
                this.courseName,
                this.gradeValue,
                this.gradeDescription,
                this.gradeType,
                this.weight,
                this.createdAt
        );
    }

    @Override
    public String toString() {
        return "CourseGradeDetail{" +
                "gradeValue=" + gradeValue +
                ", gradeType='" + gradeType + '\'' +
                ", weight=" + weight +
                "}";
    }
}
