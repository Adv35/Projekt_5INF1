package com.adv;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class StudentDashboardPanel extends JPanel implements ActionListener {

    private App mainApp;
    private JLabel welcomeLabel;
    private JLabel overallAvgLabel;
    private JPanel coursesPanel;

    // Backend Nutzung:
    private GradeCalc gradeCalc;
    private CourseDataAccess courseDataAccess;

    private User student;

    public StudentDashboardPanel(App mainApp) {
        this.mainApp = mainApp;
        this.gradeCalc = new GradeCalc();
        this.courseDataAccess = new CourseDataAccess();

        setLayout(new BorderLayout(0, 50));
        setBorder(new EmptyBorder(10, 0, 10, 0));

        // --- Wilkommensnachricht ---
        JPanel topPanel = new JPanel(new GridBagLayout());

        JPanel labelsPanel = new JPanel();
        labelsPanel.setLayout(new BoxLayout(labelsPanel, BoxLayout.Y_AXIS));
        welcomeLabel = new JLabel("Willkommen!", SwingConstants.LEFT);
        welcomeLabel.setFont(new Font("Helvetica", Font.BOLD, 24));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelsPanel.add(welcomeLabel);

        // --- Notendurchschnitt ---
        overallAvgLabel = new JLabel("Gesamtdurchschnitt:       ");
        overallAvgLabel.setFont(new Font("Helvetica", Font.ITALIC, 16));
        overallAvgLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelsPanel.add(overallAvgLabel);

        topPanel.add(labelsPanel);
        add(topPanel, BorderLayout.NORTH);


        // --- Panel für Kurse --
        coursesPanel = new JPanel();
        coursesPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        // coursesPanel in ein ScrollPane, damit scrollen geht
        JScrollPane scrollPane = new JScrollPane(coursesPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBorder(null); // Default-Border entfernen
        add(scrollPane, BorderLayout.CENTER);
    }


    public void loadStudentData(User student) {
        this.student = student;

        welcomeLabel.setText("Willkommen, " + student.getFirstName() + " " + student.getLastName() + "!");

        // --- Gesamtdurchschnitt berechnen und anzeigen --
        float overallAvg = gradeCalc.getOverallStudentAvg(student.getId());
        if (!Float.isNaN(overallAvg)) {
            overallAvgLabel.setText("Gesamtdurchschnitt: " + Math.abs(overallAvg) + " ");
        } else {
            overallAvgLabel.setText("Gesamtdurchschnitt: N/A");
        }

        // --- Kursboxen machen ---
        coursesPanel.removeAll(); // Alles im Panel resetten
        for (Course course : courseDataAccess.findCoursesByStudentId(student.getId())) {
            JButton courseButton = new JButton("<html><center>" + course.getName() + "</center></html>");
            courseButton.setActionCommand(course.getCourseId());
            courseButton.addActionListener(this);
            courseButton.setPreferredSize(new Dimension(200, 150));

            JPanel buttonWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
            buttonWrapper.add(courseButton);

            coursesPanel.add(buttonWrapper);
        }

        coursesPanel.revalidate();
        coursesPanel.repaint();

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String courseId = e.getActionCommand();
        //JOptionPane.showMessageDialog(mainApp, "Kurs geklickt! ID: " + courseId + "\nDetails in Implementierung");
        mainApp.getStudentCourseDetailPanel().loadCourseData(student, courseId);
        mainApp.showPanel(App.STUDENT_COURSE_DETAIL_PANEL);
    }
}
