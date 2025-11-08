package com.adv;

public class Grade {

    private final String gradeId;
    private final String studentId;
    private final String courseId;
    private final float gradeValue;
    private final String gradeDescription;
    private final String gradeType;
    private final String enteredBy;

    public Grade(String gradeId, String studentId, String courseId, float gradeValue, String gradeDescription, String gradeType, String enteredBy) {
        this.gradeId = gradeId;
        this.studentId = studentId;
        this.courseId = courseId;
        this.gradeValue = gradeValue;
        this.gradeDescription = gradeDescription;
        this.gradeType = gradeType;
        this.enteredBy = enteredBy;
    }

    public Grade(String studentId, String courseId, float gradeValue, String gradeDescription, String gradeType, String enteredBy) {
        this(null, studentId, courseId, gradeValue, gradeDescription, gradeType, enteredBy);
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

    public String getGradeDescription() {
        return this.gradeDescription;
    }

    public String getGradeType() {
        return this.gradeType;
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
                ", gradeDescription='" + gradeDescription + '\'' +
                ", gradeType='" + gradeType + '\'' +
                ", enteredBy='" + enteredBy + '\'' +
                '}';
    }
}
