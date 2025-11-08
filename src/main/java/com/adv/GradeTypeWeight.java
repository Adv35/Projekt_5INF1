package com.adv;

public class GradeTypeWeight {

    private final String gradeTypeWeightId;
    private final String courseId;
    private final String gradeType;
    private final float weight;

    public GradeTypeWeight(String gradeTypeWeightId,String courseId, String gradeType, float weight) {
        this.gradeTypeWeightId = gradeTypeWeightId;
        this.courseId = courseId;
        this.gradeType = gradeType;
        this.weight = weight;
    }

    public GradeTypeWeight(String courseId, String gradeType, float weight) {
        this(null, courseId, gradeType, weight);
    }

    public String getGradeTypeWeightId() {
        return this.gradeTypeWeightId;
    }

    public String getCourseId() {
        return this.courseId;
    }

    public String getGradeType() {
        return this.gradeType;
    }

    public float getWeight() {
        return this.weight;
    }

    @Override
    public String toString() {
        return "GradeTypeWeight{" +
                "gradeTypeWeightId='" + gradeTypeWeightId + '\'' +
                ", courseId='" + courseId + '\'' +
                ", gradeType='" + gradeType + '\'' +
                ", weight=" + weight +
                '}';
    }
}
