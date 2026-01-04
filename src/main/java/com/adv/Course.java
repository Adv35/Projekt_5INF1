package com.adv;

public class Course {
    private final String courseId;
    private final String name;
    private final String teacherId;
    private final String description;

    public Course(String courseId, String name, String teacherId, String description) {
        this.courseId = courseId;
        this.name = name;
        this.teacherId = teacherId;
        this.description = description;
    }

    public Course (String name, String teacherId, String description) {
        this(null, name, teacherId, description);
    }

    public String getId() {
        return courseId;
    }

    public String getName() {
        return name;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseId='" + courseId + '\'' +
                ", name='" + name + '\'' +
                ", teacherId='" + teacherId + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
