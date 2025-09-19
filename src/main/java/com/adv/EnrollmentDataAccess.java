package com.adv;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class EnrollmentDataAccess {
    private final Database db = new Database();

    public boolean enrollStudent(String studentId, String courseId) {
        String sql = "INSERT INTO enrollments (student_id, course_id) VALUES (?::uuid, ?::uuid)";

        try (Connection conn = db.connect();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, studentId);
            preparedStatement.setString(2, courseId);
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error enrolling student: " + e.getMessage());
            return false;
        }
    }

}
