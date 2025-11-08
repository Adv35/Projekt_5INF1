package com.adv;

public class Enrollment {
    private String enrollmentId;
    private String studentId;
    private String courseId;

    public Enrollment(String enrollmentId, String studentId, String courseId) {
        this.enrollmentId = enrollmentId;
        this.studentId = studentId;
        this.courseId = courseId;
    }

    public Enrollment(String studentId, String courseId) {
        this(null, studentId, courseId);
    }

    public String getEnrollmentId() {
        return this.enrollmentId;
    }

    public String getStudentId() {
        return this.studentId;
    }

    public String getCourseId() {
        return this.courseId;
    }

    @Override
    public String toString() {
        return "Enrollment{" +
                "enrollmentId='" + enrollmentId + '\'' +
                ", studentId='" + studentId + '\'' +
                ", courseId='" + courseId + '\'' +
                '}';
    }


}
