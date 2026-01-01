package com.adv;

import java.sql.Timestamp;

public class StudentGradeDetail {
    private final String courseId;
    private final String courseName;
    private final Float gradeValue; // Nutzen der Wrapper - Klasse, um NULL auch verarbeiten zu können
    private final String gradeDescription;
    private final String gradeType;
    private final Float weight;
    private final Timestamp createdAt;

    public StudentGradeDetail(String courseId,
                              String courseName,
                              Float gradeValue,
                              String gradeDescription,
                              String gradeType,
                              Float weight,
                              Timestamp createdAt) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.gradeValue = gradeValue;
        this.gradeDescription = gradeDescription;
        this.gradeType = gradeType;
        this.weight = weight;
        this.createdAt = createdAt;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
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

    @Override
    public String toString() {
        return "StudentGradeDetail{" +
                "courseName= " + courseName + "\'" +
                ", gradeValue=" + gradeValue +
                ", gradeType='" + gradeType + '\'' +
                ", weight=" + weight +
                "}";
    }
}
