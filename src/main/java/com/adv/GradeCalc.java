package com.adv;


import java.util.ArrayList;
import static java.lang.Float.NaN; // NaN -- Not a Number


public class GradeCalc {
    private GradeDataAccess gradeDataAccess;
    private GradeTypeWeightDataAccess gradeTypeWeightDataAccess;
    private CourseDataAccess courseDataAccess;
    private EnrollmentDataAccess enrollmentDataAccess;

    /**
     * Initialisiert die Attribute.
     **/
    public GradeCalc() {
        this.gradeDataAccess = new GradeDataAccess();
        this.gradeTypeWeightDataAccess = new GradeTypeWeightDataAccess();
        this.courseDataAccess = new CourseDataAccess();
        this.enrollmentDataAccess = new EnrollmentDataAccess();
    }

    public float getStudentCourseAvg(String studentId, String courseId) {
        if (studentId != null && courseId != null) {
            return calculateStudentCourseAvg(studentId, courseId);
        }

        System.err.println("studentID oder courseID sind null!");
        return Float.NaN;
    }

    public float getOverallStudentAvg(String studentId) {
        if(studentId != null) {
            return calculateOverallStudentAvg(studentId);
        }
        return Float.NaN;
    }

    public float getCourseAvg(String courseId) {
        if (courseId != null) {
            return calculateCourseAvg(courseId);
        }
        return Float.NaN;
    }


    /**
     * Berchnet den Notendurchschnitt eines Kurses mithilfe der einzelnen Schülernotendurchschnitte im Kurs.
     * Schüler ohne Noten / dessen Schnitt nicht berechnet werden konnte, werden nicht einbezogen.
     **/
    private float calculateCourseAvg(String courseId) {
        // Alle, die in Kurs xy eingeschrieben sind:
        ArrayList<Enrollment> enrollments = enrollmentDataAccess.getEnrollmentsByCourseId(courseId);

        if(enrollments.isEmpty()) {
            System.err.println("Kein Schüler ist in den Kurs eingeschrieben. " +
                    "\nKursID: " + courseId);
            return Float.NaN;
        }

        float avgGrade = 0.0f; // Temporäre Variable zur Berechnung des Kursdurchschnitts.
        int studentCount = 0;   // Anzahl der Schüler (die Noten haben)

        for (Enrollment enrollment : enrollments) {
            String studentId = enrollment.getStudentId();

            if (studentId == null) {    // Wenn eine Einschreibung keinen Schüler hat:
                System.err.println("Eine Kurszuweisung hat keine Student-ID. " +
                        "\nFolgende Details: " + enrollment.toString());
                return Float.NaN;
            }

            float studentAvg = calculateStudentCourseAvg(studentId, courseId);
            if (!Float.isNaN(studentAvg)) {
                avgGrade += studentAvg; // Alle Schülerdurchschnittsnoten werden aufaddiert
                studentCount++;
            }

        }
        if (studentCount > 0) {
            return (avgGrade / studentCount);
        }
        System.err.println("Berechnung des Durchschnitts fehlgeschlagen. Bitte checken Sie die Anzahl der Schüler mit Noten. " +
                "\navgGrade: " + avgGrade +
                "\nstudentCount: " + studentCount);
        return Float.NaN;
    }

    //g1
    private float calculateOverallStudentAvg(String studentId) {
        ArrayList<Course> courses = courseDataAccess.findCoursesByStudentId(studentId);
        float avgGrade = 0.0f;
        int coursesCounter = 0;

        for(Course course : courses) {

            String courseId = course.getCourseId();
            if (courseId == null) {
                System.err.println("Ein Kurs hat keine Course-ID. " +
                        "\nFolgende Details: " + course.toString());
                return Float.NaN;
            }

            float avgCourseGrade = calculateStudentCourseAvg(studentId, courseId);
            if (!Float.isNaN(avgCourseGrade)) {
                avgGrade += avgCourseGrade;
                coursesCounter++;
            }


        }
        if (avgGrade >= 0 && coursesCounter > 0) {
            return (avgGrade / coursesCounter);
        }
        System.err.println("Berechnung des Durchschnitts fehlgeschlagen. " +
                "\navgGrade: " + avgGrade +
                "\ncoursesCounter: " + coursesCounter);
        return Float.NaN;
    }

    // g1
    /**
     * Wenn man in einem Typ keine Noten hat, wird dieser Typ nicht bei der Berechnung einbezogen.
     * **/
    private float calculateStudentCourseAvg(String studentId, String courseId) {

        ArrayList<GradeTypeWeight> weights = gradeTypeWeightDataAccess.getWeightsForCourse(courseId);
        ArrayList<Grade> grades = gradeDataAccess.getGradesForStudentInCourse(studentId, courseId);

        float avgGrade = 0.0f;
        for (GradeTypeWeight weight : weights) {
            float sumOfGradesForType = 0.0f;
            int countOfGradesForType = 0;
            boolean hasMinOneGrade = false;

            for (Grade grade : grades) {
                if (weight.getGradeType().equals(grade.getGradeType())) {
                    sumOfGradesForType += grade.getGradeValue();
                    countOfGradesForType++;
                }
            }
            if (countOfGradesForType > 0) {
                avgGrade += (sumOfGradesForType / countOfGradesForType) * weight.getWeight();
            }
        }

        if (avgGrade >= 0 && !grades.isEmpty()) {
            return (avgGrade);
        } else if (grades.isEmpty()) {
            return Float.NaN;
        }
        System.err.println("Berechnung des Durchschnitts fehlgeschlagen. " +
                "\navgGrade: " + avgGrade);
        return Float.NaN;
    }
}
