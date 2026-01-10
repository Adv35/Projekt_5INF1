package com.adv;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Klasse für die Detailansicht eines Kurses für die Schüler.
 * Hat alle relevanten Informationen für die Kursansicht
 * (Kursname, Lehrer, Gewichtungen, Noten) zusammen.
 * Dies reduziert die Menge der notwendigen Datenbankverbindungen und optimiert die Laufzeit (die echte Zeit).
 *
 * @author Advik Vattamwar
 * @version 10.01.2026
 */
public class StudentCourseViewData {
    private final String COURSE_NAME;
    private final String TEACHER_LAST_NAME;
    private final HashMap<String, Float> WEIGHTS;
    private final ArrayList<StudentGradeDetail> GRADES;

    public StudentCourseViewData(String courseName,
                                 String teacherLastName,
                                 HashMap<String, Float> weights,
                                 ArrayList<StudentGradeDetail> grades)
    {
        this.COURSE_NAME = courseName;
        this.TEACHER_LAST_NAME = teacherLastName;
        this.WEIGHTS = weights;
        this.GRADES = grades;
    }

    /** Getter-Methode.
     * @return Gibt den Namen des Kurses zurück.
     * **/
    public String getCourseName() {
        return COURSE_NAME;
    }

    /** Getter-Methode.
     * @return Gibt den Nachnamen der Lehrkraft zurück.
     * **/
    public String getTeacherLastName() {
        return TEACHER_LAST_NAME;
    }

    /** Getter-Methode.
     * @return Gibt eine HashMap mit allen Gewichten zu einem Kurs zurück.
     * **/
    public HashMap<String, Float> getWeights() {
        return WEIGHTS;
    }

    /** Getter-Methode.
     * @return Gibt eine ArrayList voller Noten von einem Schüler zu dem Kurs zurück.
     * **/
    public ArrayList<StudentGradeDetail> getGrades() {
        return GRADES;
    }

    /**
     * Abbildung des StudentCourseViewData Objekts als String mit seinen Werten.
     * **/
    @Override
    public String toString() {
        return "StudentCourseViewData{" +
                "COURSE_NAME='" + COURSE_NAME + '\'' +
                ", TEACHER_LAST_NAME='" + TEACHER_LAST_NAME + '\'' +
                ", WEIGHTS=" + WEIGHTS +
                ", GRADES=" + GRADES +
                '}';
    }
}
