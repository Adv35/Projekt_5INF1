package com.adv;

public class Grade {

    private final String gradeId;
    private final String studentId;
    private final String courseId;
    private final float gradeValue;
    private final String gradeType;
    private final float weight;
    private final String enteredBy;

    public Grade(String gradeId, String studentId, String courseId, float gradeValue, String gradeType, float weight, String enteredBy) {
        this.gradeId = gradeId;
        this.studentId = studentId;
        this.courseId = courseId;
        this.gradeValue = gradeValue;
        this.gradeType = gradeType;
        this.weight = weight;
        this.enteredBy = enteredBy;
    }

    public Grade(String studentId, String courseId, float gradeValue, String gradeType, float weight, String enteredBy) {
        this(null, studentId, courseId, gradeValue, gradeType, weight, enteredBy);
    }

    public String getGradeId() {
        return this.gradeId;
    }

    public String getStudentId() {
        return this.studentId;
    }

    public String getCourseId() {
        return this.courseId;
    }

    public float getGradeValue() {
        return this.gradeValue;
    }

    public String getGradeType() {
        return this.gradeType;
    }

    public float getWeight() {
        return this.weight;
    }

    public String getEnteredBy() {
        return this.enteredBy;
    }

    @Override
    public String toString() {
        return "Grade{" +
                "gradeId='" + gradeId + '\'' +
                ", studentId='" + studentId + '\'' +
                ", courseId='" + courseId + '\'' +
                ", gradeValue=" + gradeValue +
                ", gradeType='" + gradeType + '\'' +
                ", weight=" + weight +
                ", enteredBy='" + enteredBy + '\'' +
                '}';
    }
}
