package com.adv;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GradeTypeWeightDataAccess {

    private final Database db = new Database();

    public boolean setWeightForGradeType(GradeTypeWeight gradeTypeWeight) {
        // mögliche Types sind: mündlich, schriftlich, fachpraktisch und Test
        String sql = "INSERT INTO grade_type_weight (course_id, grade_type, weight) VALUES (?::uuid, ?::grade_type, ?)" +
                " ON CONFLICT (course_id, grade_type) DO UPDATE SET weight = EXCLUDED.weight";

        try (Connection conn = db.connect();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setString(1, gradeTypeWeight.getCourseId());
            preparedStatement.setString(2, gradeTypeWeight.getGradeType());
            preparedStatement.setFloat(3, gradeTypeWeight.getWeight());

            preparedStatement.executeUpdate();
            return true; // Wenn die Operation erfolgreich war, ist true

        } catch (SQLException e) {
            System.err.println("Error setting weight for grade type: " + e.getMessage());
            return false;
        }

    }

    public ArrayList<GradeTypeWeight> getWeightsForCourse(String courseId) {
        ArrayList<GradeTypeWeight> weights = new ArrayList<>();
        String sql = "SELECT * FROM grade_type_weight WHERE course_id = ?::uuid";

        try (Connection conn = db.connect();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, courseId);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    weights.add(mapRowToGradeTypeWeight(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting weights for course: " + e.getMessage());
        }
        return weights;
    }

    private GradeTypeWeight mapRowToGradeTypeWeight(ResultSet rs) throws SQLException {
        return new GradeTypeWeight(
                rs.getString("grade_type_weight_id"),
                rs.getString("course_id"),
                rs.getString("grade_type"),
                rs.getFloat("weight")
        );
    }
}
