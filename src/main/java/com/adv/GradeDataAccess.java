package com.adv;

import jdk.jfr.SettingDefinition;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException;


public class GradeDataAccess {

    public GradeDataAccess() {}

    private final Database db = new Database();

    public boolean createGrade(Grade grade) {
        String sql = "INSERT INTO grades (student_id, course_id, grade_value, grade_type, weight, entered_by)"+
                " VALUES (?::uuid, ?::uuid, ?, ?, ?, ?::uuid)";

        try (Connection conn = db.connect();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setString(1, grade.getStudentId());
            preparedStatement.setString(2, grade.getCourseId());
            preparedStatement.setFloat(3, grade.getGradeValue());
            preparedStatement.setString(4, grade.getGradeType());
            preparedStatement.setFloat(5, grade.getWeight());
            preparedStatement.setString(6, grade.getEnteredBy());

            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;

        } catch (Exception e) {
            System.err.println("Error creating grade: " + e.getMessage());
            return false;
        }
    }

    public ArrayList<Grade> getGradesForStudentInCourse(String studentId, String courseId) {
        ArrayList<Grade> grades = new ArrayList<>();
        String sql = "SELECT * FROM grades WHERE student_id = ?::uuid AND course_id = ?::uuid";

        try (Connection conn = db.connect();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setString(1, studentId);
            preparedStatement.setString(2, courseId);

            try (ResultSet resultSet = preparedStatement.executeQuery()){

                while (resultSet.next()) {
                    grades.add(mapRowToGrade(resultSet));
                }

            }

        } catch (SQLException e) {
            System.err.println("Error getting grades for student in course: " + e.getMessage());
        }
        return grades;
    }


    private Grade mapRowToGrade(ResultSet resultSet) throws SQLException {
        return new Grade(
                resultSet.getString("grade_id"),
                resultSet.getString("student_id"),
                resultSet.getString("course_id"),
                resultSet.getFloat("grade_value"),
                resultSet.getString("grade_type"),
                resultSet.getFloat("weight"),
                resultSet.getString("entered_by")
        );
    }



}
