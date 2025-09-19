package com.adv;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * CourseDataAccess is responsible for all database operations related to the Course.
 */

public class CourseDataAccess {

    private final Database db = new Database();

    // Every course of a teacher
    public ArrayList<Course> findCoursesByTeacherId(String teacherId) {
        ArrayList<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM courses WHERE teacher_id = ?::uuid";

        try ( Connection conn = db.connect();
              PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, teacherId);

            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    courses.add(mapRowToCourse(resultSet));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error finding course by teacher: " + e.getMessage());
        }
    return courses;
    }

    public Course findCourseById(String courseId) {
        String sql = "SELECT * FROM courses WHERE course_id = ?::uuid";

        try(Connection conn = db.connect();
            PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, courseId);

            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) { // "if-clause" cuz there's only one matching row
                    return mapRowToCourse(resultSet);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error finding course by ID: " + e.getMessage());
        }
        return null;
    }

    public ArrayList<Course> findCoursesByStudentId(String studentId) {
        ArrayList<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM courses, enrollments " +
                "WHERE courses.course_id = enrollments.course_id " +
                "AND enrollments.student_id = ?::uuid";

        try(Connection conn = db.connect();
            PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, studentId);

            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    courses.add(mapRowToCourse(resultSet));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding courses by student ID: " + e.getMessage());
        }
        return courses;
    }

    private Course mapRowToCourse(ResultSet resultSet) throws SQLException {
        return new Course(
                resultSet.getString("course_id"),
                resultSet.getString("course_name"),
                resultSet.getString("teacher_id"),
                resultSet.getString("description")
        );
    }

}
