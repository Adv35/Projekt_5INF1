package com.adv;

import java.util.ArrayList;
import java.util.HashMap;

public class StudentCourseViewData {
    private final String courseName;
    private final String teacherLastName;
    private final HashMap<String, Float> weights;
    private final ArrayList<StudentGradeDetail> grades;

    public StudentCourseViewData(String courseName,
                                 String teacherLastName,
                                 HashMap<String, Float> weights,
                                 ArrayList<StudentGradeDetail> grades)
    {
        this.courseName = courseName;
        this.teacherLastName = teacherLastName;
        this.weights = weights;
        this.grades = grades;
    }


    public String getCourseName() {
        return courseName;
    }

    public String getTeacherLastName() {
        return teacherLastName;
    }

    public HashMap<String, Float> getWeights() {
        return weights;
    }

    public ArrayList<StudentGradeDetail> getGrades() {
        return grades;
    }

    @Override
    public String toString() {
        return "StudentCourseViewData{" +
                "courseName='" + courseName + '\'' +
                ", teacherLastName='" + teacherLastName + '\'' +
                ", weights=" + weights +
                ", grades=" + grades +
                '}';
    }
}
