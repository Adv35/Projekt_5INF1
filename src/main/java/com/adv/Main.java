package com.adv;

import javax.swing.*;
import com.formdev.flatlaf.FlatLightLaf;

public class Main {
    /* public static void main(String[] args) {
        // Instantiate our Data Access Object
        UserDataAccess userDataAccess = new UserDataAccess();
        CourseDataAccess courseDataAccess = new CourseDataAccess();
        EnrollmentDataAccess enrollmentDataAccess = new EnrollmentDataAccess();
        PasswordManagement passwordManagement = new PasswordManagement();
        GradeDataAccess gradeDataAccess = new GradeDataAccess();
        GradeTypeWeightDataAccess gtwDataAccess = new GradeTypeWeightDataAccess();
        GradeCalc gradeCalc = new GradeCalc();


        // --- Use the DAO to find a specific user (like for a login) ---
        System.out.println("Searching for user 'mustermax'...");
        User foundUser = userDataAccess.findUserByUsername("mustermax");


        if (foundUser != null) {
            System.out.println("Found user: " + foundUser.toString());
        } else {
            System.out.println("User not found.");
        }

        // --- Admin creates a new user with a secure password ---
        System.out.println("\n--- Admin Action: Creating a new user ---");
        String initialPassword = "temporaryPassword123";
        String hashedPassword = passwordManagement.hashPassword(initialPassword);
        System.out.println("Admin is creating user 'testuser' with a salted and hashed password.");
        User newUser = new User("Test", "User", "testuser", hashedPassword, "test@school.com", "student");
        userDataAccess.createUser(newUser);

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

        // --- Admin/Teacher Action: Setting weights for a course ---
        System.out.println("\n--- Admin/Teacher Action: Setting grade type weights ---");
        gtwDataAccess.setWeightForGradeType(new GradeTypeWeight(courseIdToEnrollIn, "Schriftlich", 0.5f)); // 50%
        gtwDataAccess.setWeightForGradeType(new GradeTypeWeight(courseIdToEnrollIn, "Test", 0.3f)); // 30%
        gtwDataAccess.setWeightForGradeType(new GradeTypeWeight(courseIdToEnrollIn, "Mündlich", 0.2f)); // 20%
        System.out.println("Weights for course " + courseIdToEnrollIn + " have been set.");

        // --- User changes their own password ---
        System.out.println("\n--- User Action: Changing password for 'testuser' ---");
        User userToUpdate = userDataAccess.findUserByUsername("testuser");
        if (userToUpdate != null) {
            String oldPasswordAttempt = "temporaryPassword123";
            String newPassword = "aNewSecurePassword!";

            // 1. Verify the user's old password
            if (passwordManagement.checkPassword(oldPasswordAttempt, userToUpdate.getPasswordHash())) {
                System.out.println("Old password verified. Updating to new password...");

                // 2.Hash the new password
                String newHashedPassword = passwordManagement.hashPassword(newPassword);

                // 3. Update the database
                boolean success = userDataAccess.updatePassword(userToUpdate.getId(), newHashedPassword);
                if (success) {
                    System.out.println("Password for 'testuser' has been updated successfully.");
                } else {
                    System.err.println("Failed to update password in the database.");
                }
            } else {
                System.err.println("Incorrect old password. Password change failed.");
            }
        }

        // --- Admin resets another user's password ---
        System.out.println("\n--- Admin Action: Resetting password for 'testuser' ---");
        User userToReset = userDataAccess.findUserByUsername("testuser");
        if (userToReset != null) {
            String newAdminSetPassword = "newPasswordByAdmin456";
            System.out.println("Admin is resetting password for user: " + userToReset.getUsername());

            // 1. Hash the new password (no verification of old password needed)
            String newHashedPassword = passwordManagement.hashPassword(newAdminSetPassword);

            // 2. Update the database directly
            boolean success = userDataAccess.updatePassword(userToReset.getId(), newHashedPassword);
            if (success) {
                System.out.println("Password for '" + userToReset.getUsername() + "' has been reset successfully.");
            } else {
                System.err.println("Failed to reset password in the database.");
            }
        }

//        // --- Simulate a Teacher adding a grade ---
//        System.out.println("\n--- Teacher Action: Adding a grade ---");
//        // For this test, we use the same student/course/teacher IDs from above
//        Grade newGrade = new Grade(
//                studentIdToEnroll,
//                courseIdToEnrollIn,
//                1.3f,         // Grade value
//                "Klausur Nr. 1",       // Grade description
//                "Schriftlich",               // Grade type
//                teacherIdToFind      // The teacher entering the grade
//        );
//        boolean wasGradeCreated = gradeDataAccess.createGrade(newGrade);
//        if (wasGradeCreated) {
//            System.out.println("Success! A new grade was added.");
//        } else {
//            System.err.println("Failed to add grade.");
//        }

        // Add multiple grades for a better test
        gradeDataAccess.createGrade(new Grade(studentIdToEnroll, courseIdToEnrollIn, 1.3f, "Final term exam", "Schriftlich", teacherIdToFind));
        gradeDataAccess.createGrade(new Grade(studentIdToEnroll, courseIdToEnrollIn, 2.0f, "Mid-term exam", "Schriftlich", teacherIdToFind));
        gradeDataAccess.createGrade(new Grade(studentIdToEnroll, courseIdToEnrollIn, 1.0f, "Pop quiz", "Test", teacherIdToFind));
        gradeDataAccess.createGrade(new Grade(studentIdToEnroll, courseIdToEnrollIn, 2.7f, "Presentation", "Mündlich", teacherIdToFind));
        System.out.println("Several grades have been added for the student.");

        // --- Simulate a Student viewing their grades for that course ---
        System.out.println("\n--- Student Action: Viewing my grades for a course ---");
        ArrayList<Grade> myGrades = gradeDataAccess.getGradesForStudentInCourse(studentIdToEnroll, courseIdToEnrollIn);
        System.out.println("Grades for student " + studentIdToEnroll + " in course " + courseIdToEnrollIn + ":");
        for (Grade grade : myGrades) {
            System.out.println(" - " + grade.toString());
        }

        // --- Use the GradeCalc to calculate the student's average for that course ---
        System.out.println("\n--- System Action: Calculating student's course average ---");
        float studentCourseAverage = gradeCalc.getStudentCourseAvg(studentIdToEnroll, courseIdToEnrollIn);
        if (!Float.isNaN(studentCourseAverage)) {
            System.out.println("The final calculated grade for the student in this course is: " + studentCourseAverage);
            // Expected: ((1.3+2.0)/2 * 0.5) + (1.0 * 0.3) + (2.7 * 0.2) = (1.65 * 0.5) + 0.3 + 0.54 = 0.825 + 0.3 + 0.54 = 1.665
        } else {
            System.err.println("Failed to calculate student's course average.");
        }

        // --- Use the GradeCalc to calculate the overall student average ---
        System.out.println("\n--- System Action: Calculating overall student average ---");
        float overallAverage = gradeCalc.getOverallStudentAvg(studentIdToEnroll);
        if (!Float.isNaN(overallAverage)) {
            System.out.println("The overall average for this student across all courses is: " + overallAverage);
        } else {
            System.out.println("The student has no grades in any course yet.");
        }

        // --- Use the GradeCalc to calculate the average for the entire course ---
        System.out.println("\n--- System Action: Calculating average for the entire course ---");
        float courseAverage = gradeCalc.getCourseAvg(courseIdToEnrollIn);
        if (!Float.isNaN(courseAverage)) {
            System.out.println("The average grade for the entire course " + courseIdToEnrollIn + " is: " + courseAverage);
        } else {
            System.out.println("The course has no students with grades yet.");
        }





//        // --- Simulate a Student viewing their grades for that course ---
//        System.out.println("\n--- Student Action: Viewing my grades for a course ---");
//        ArrayList<Grade> myGrades = gradeDataAccess.getGradesForStudentInCourse(studentIdToEnroll, courseIdToEnrollIn);
//        System.out.println("Grades for student " + studentIdToEnroll + " in course " + courseIdToEnrollIn + ":");
//        for (Grade grade : myGrades) {
//            System.out.println(" - " + grade.toString());
//        }
    } */
    // "newPasswordByAdmin456"
    public static void main(String[] args) {
        FlatLightLaf.setup();
        // Ja also das da unten macht man halt so, keine Ahnung warum; hab es eig eh aus internet kopiert
        SwingUtilities.invokeLater(() -> {
            App app = new App();
            app.setVisible(true);
        });
    }
}