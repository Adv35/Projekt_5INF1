package com.adv;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class TeacherCoursePanel extends CommonJPanel implements ActionListener {
    private App mainApp;
    private CourseDataAccess courseDataAccess;
    private EnrollmentDataAccess enrollmentDataAccess;
    private UserDataAccess userDataAccess;
    private GradeDataAccess gradeDataAccess;
    private GradeCalc gradeCalc;

    private String currentCourseId;
    private User student;
    private User teacher;

    private JPanel contentPanel;
    private JButton backButton;
    private JButton editWeightsButton;
    private JButton studentButton;
    private JButton deleteWeightButton;
    private JButton infoButton;

    public TeacherCoursePanel(App mainApp) {
        this.mainApp = mainApp;
        this.courseDataAccess = new CourseDataAccess();
        this.enrollmentDataAccess = new EnrollmentDataAccess();
        this.userDataAccess = new UserDataAccess();
        this.gradeDataAccess = new GradeDataAccess();
        this.gradeCalc = new GradeCalc();

        setLayout(new BorderLayout());

        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBorder(null);
        add(scrollPane, BorderLayout.CENTER);

        backButton = new JButton("Zurück");
        backButton.addActionListener(this);
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void loadCourseData(String courseId, User teacher) {
        this.teacher = teacher;
        this.currentCourseId = courseId;
        contentPanel.removeAll();

        Course course = courseDataAccess.findCourseById(courseId);

        // --- Kopfzeile ---
        JLabel courseNameLabel = new JLabel(course.getName());
        courseNameLabel.setFont(new Font("Segoe UI", Font.BOLD, 30));
        courseNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(courseNameLabel);

        JLabel courseAverageLabel = new JLabel();
        float courseAvgGrade = gradeCalc.getCourseAvg(courseId);
        courseAverageLabel.setText("Kursdurchschnitt - " + courseAvgGrade);
        courseAverageLabel.setFont(new Font("Segoe UI", Font.ITALIC, 16));
        courseAverageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(courseAverageLabel);

        contentPanel.add(Box.createVerticalStrut(30));

        // --- Gewichtung ---
        addSectionHeader("Gewichtung");
        HashMap<String, Float> weights = gradeDataAccess.getWeightsForCourse(courseId);
        if (weights.isEmpty()) {
            addInfoLabel("Keine Gewichtungen definiert.");
        } else {
            for (HashMap.Entry<String, Float> entry : weights.entrySet()) {
                JPanel weightRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
                JLabel weightLabel = new JLabel(entry.getKey() + ": " + entry.getValue() + "%");
                weightLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));

                deleteWeightButton = new JButton("X");
                deleteWeightButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
                deleteWeightButton.setForeground(Color.RED);
                deleteWeightButton.setBorderPainted(false);
                deleteWeightButton.setContentAreaFilled(false);
                deleteWeightButton.setFocusPainted(false);
                deleteWeightButton.setActionCommand(entry.getKey());
                deleteWeightButton.addActionListener(this);

                weightRow.add(weightLabel);
                weightRow.add(deleteWeightButton);
                weightRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, weightRow.getPreferredSize().height));
                contentPanel.add(weightRow);
            }
        }

        editWeightsButton = new JButton("Gewichtung hinzufügen/ändern");
        editWeightsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        editWeightsButton.addActionListener(this);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(editWeightsButton);
        contentPanel.add(Box.createVerticalStrut(10));

        // --- Schülerliste ---
        addSectionHeader("Schülerliste");
        ArrayList<Enrollment> enrollments = enrollmentDataAccess.getEnrollmentsByCourseId(courseId);
        if (enrollments.isEmpty()) {
            addInfoLabel("Keine Schüler eingeschrieben.");
        } else {
            for (Enrollment enrollment : enrollments) {
                student = userDataAccess.findUserById(enrollment.getStudentId());
                if (student != null) {
                    studentButton = new JButton(student.getFirstName() + " " + student.getLastName());
                    studentButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                    studentButton.setMaximumSize(new Dimension(300, 40));
                    studentButton.setFont(new Font("Segoe UI", Font.PLAIN, 16));

                    studentButton.addActionListener(this);

                    contentPanel.add(studentButton);
                    contentPanel.add(Box.createVerticalStrut(10));
                }
            }
        }
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void showEditWeightDialog(String courseId) {
        JTextField typeField = new JTextField();
        JTextField weightField = new JTextField();
        infoButton = new JButton("[i]");

        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        labelPanel.add(new JLabel("Typ (z.B. Schriftlich):"));
        labelPanel.add(infoButton, FlowLayout.CENTER);

        Object[] message = {
                labelPanel, typeField,
                "Gewichtung (in Prozent): " , weightField
        };

        infoButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        infoButton.setBorderPainted(false);
        infoButton.setContentAreaFilled(false);
        infoButton.setForeground(Color.BLUE);
        infoButton.setFocusPainted(false);
        infoButton.addActionListener(this);


        int option = JOptionPane.showConfirmDialog(null, message, "Gewichtung setzen", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String type = typeField.getText();
                float weight = Math.abs(Float.parseFloat(weightField.getText()));
                gradeDataAccess.setWeightForGradeType(courseId, type, weight);
                loadCourseData(courseId, this.teacher);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Fehler: " + e.getMessage());
            }
        }

    }

    @Override
    public void refreshData() {
        if (this.currentCourseId != null && this.teacher != null) {
            loadCourseData(this.currentCourseId, this.teacher);
        }
    }

    private void addSectionHeader(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 18));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(label);
        contentPanel.add(Box.createVerticalStrut(10));
    }

    private void addInfoLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(label);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            mainApp.showPanel(App.TEACHER_DASHBOARD_PANEL);
        } else if (e.getSource() == editWeightsButton) {
            showEditWeightDialog(currentCourseId);
        } else if (e.getSource() == studentButton) {
            mainApp.getTeacherGradingPanel().loadGradingData(currentCourseId, student, this.teacher);
            mainApp.showPanel(App.TEACHER_GRADING_PANEL);
        } else if (e.getSource() == deleteWeightButton) {
            String gradeType = e.getActionCommand();
            gradeDataAccess.deleteWeightForGradeType(currentCourseId, gradeType);
            loadCourseData(currentCourseId, this.teacher);
        } else if (e.getSource() == infoButton) {
            JOptionPane.showMessageDialog(null, "Folgende Notentypen sind möglich: \n" +
                    "1.) Schriftlich \n" +
                    "2.) Mündlich \n" +
                    "3.) Fachpraktisch \n" +
                    "4.) Test \n" +
                    "Achten Sie auf die exakte Schreibweise.");
        }
    }
}
