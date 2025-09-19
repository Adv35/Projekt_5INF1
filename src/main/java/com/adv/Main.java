package com.adv;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        // Instantiate our Data Access Object
        UserDataAccess userDataAccess = new UserDataAccess();
        CourseDataAccess courseDataAccess = new CourseDataAccess();
        EnrollmentDataAccess enrollmentDataAccess = new EnrollmentDataAccess();


        // --- Use the DAO to find a specific user (like for a login) ---
        System.out.println("Searching for user 'mustermax'...");
        User foundUser = userDataAccess.findUserByUsername("mustermax");


        if (foundUser != null) {
            System.out.println("Found user: " + foundUser.toString());
        } else {
            System.out.println("User not found.");
        }

//        // --- Use the DAO to create a new user ---
//        System.out.println("\nCreating a new user...");
//        // In a real app, this hash would be generated from a password using a library like BCrypt.
//        String fakePasswordHash = "some_generated_hash_string";
//        User newUser = new User("Muxe", "Mustermann", "muxemustermann", fakePasswordHash, "muxe@school.com", "student");
//        userDataAccess.createUser(newUser);

        // All courses for a teacher
        String teacherIdToFind = "ad9bb451-147c-4cc1-b48e-fa1b4abd2a79";
        ArrayList<Course> teacherCourses = courseDataAccess.findCoursesByTeacherId(teacherIdToFind);

        if(!teacherCourses.isEmpty()) {
            System.out.println("Found " + teacherCourses.size() + " course(s) for teacher " + teacherIdToFind + ":");
            for (Course course : teacherCourses) {
                System.out.println(course.toString());
            }
        } else {
            System.out.println("No courses found for teacher " + teacherIdToFind);
        }

        //Find specific course
        String courseIdToFind = "8fa0fa03-ca70-4592-9dff-90bfffb62bd8";
        Course specificCourse = courseDataAccess.findCourseById(courseIdToFind);

        if (specificCourse != null) {
            System.out.println("Found course: " + specificCourse.toString());
        } else {
            System.out.println("Course not found.");
        }

        // Admin Simulation

        // --- Simulate an Admin enrolling a student in a course ---
        System.out.println("\n--- Admin Action: Enrolling a student ---");
        // !! IMPORTANT: Replace these with a real student ID and course ID from your database.
        String studentIdToEnroll = "8742335f-adc3-4e60-9f94-085584e99306"; // A student's user_id
        String courseIdToEnrollIn = "8fa0fa03-ca70-4592-9dff-90bfffb62bd8"; // A course's course_id

        boolean wasEnrolled = enrollmentDataAccess.enrollStudent(studentIdToEnroll, courseIdToEnrollIn);
        if (wasEnrolled) {
            System.out.println("Success! Student has been enrolled.");
        } else {
            System.err.println("Failed to enroll student. They might already be enrolled, or an error occurred.");
        }

        // --- Simulate a Student checking their enrolled courses ---
        System.out.println("\n--- Student Action: Viewing my courses ---");
        ArrayList<Course> myCourses = courseDataAccess.findCoursesByStudentId(studentIdToEnroll);
        if (!myCourses.isEmpty()) {
            System.out.println("Student " + studentIdToEnroll + " is enrolled in:");
            for (Course course : myCourses) {
                System.out.println(" - " + course.toString());
            }
        } else {
            System.out.println("Student " + studentIdToEnroll + " is not enrolled in any courses.");
        }

    }

}