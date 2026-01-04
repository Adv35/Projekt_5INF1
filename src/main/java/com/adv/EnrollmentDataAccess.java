package com.adv;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;

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

    public ArrayList<Enrollment> getEnrollmentsByCourseId(String courseId) {
        ArrayList<Enrollment> enrollments = new ArrayList<>();
        String sql = "SELECT * FROM enrollments WHERE course_id = ?::uuid";

        try (Connection conn = db.connect();
        PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, courseId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    enrollments.add(mapRowToEnrollment(resultSet));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error fetching Enrollments by CourseID: " + e.getMessage());
        }
        return enrollments;
    }

    public boolean checkEnrollment(String studentId, String courseId) throws RuntimeException {
        String sql = "SELECT enrollment_id FROM enrollments WHERE student_id = ?::uuid AND course_id = ?::uuid";

        try (Connection conn = db.connect();
        PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, studentId);
            preparedStatement.setString(2, courseId);

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();

        } catch (SQLException e) {
            throw new RuntimeException("Fehler beim Prüfen der Einschreibung", e);
        }
    }

    private Enrollment mapRowToEnrollment(ResultSet resultSet) throws SQLException {
        return new Enrollment(
                resultSet.getString("enrollment_id"),
                resultSet.getString("student_id"),
                resultSet.getString("course_id")
        );

    }



}
