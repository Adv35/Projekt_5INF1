package com.adv;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AdminEnrollmentPanel extends CommonJPanel implements ActionListener {

    private App mainApp;
    private EnrollmentDataAccess enrollmentDataAccess;
    private CourseDataAccess courseDataAccess;
    private UserDataAccess userDataAccess;

    private JComboBox<User> studentComboBox;
    private JComboBox<Course> courseComboBox;
    private JButton saveButton;
    private JButton backButton;

    public AdminEnrollmentPanel(App mainApp) {
        this.mainApp = mainApp;
        this.enrollmentDataAccess = new EnrollmentDataAccess();
        this.courseDataAccess = new CourseDataAccess();
        this.userDataAccess = new UserDataAccess();

        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(20,20,20,20));

        // --- Titel ---
        JLabel titleLabel = new JLabel("Schüler einschreiben", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        // --- Formular Panel ---
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // --- Felder initialisieren ---
        studentComboBox = new JComboBox<>();
        studentComboBox.setRenderer(new UserComboBoxRenderer()); // Wiederverwenden des Renderers, dass für AdminCoursePanel gemacht wurde

        courseComboBox = new JComboBox<>();
        courseComboBox.setRenderer(new CourseComboBoxRenderer()); // Neue Renderklasse für diese ComboBox


        // --- Felder zum Panel hinzufügen ---
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel studentUsernameLabel = new JLabel("Schüler auswählen");
        formPanel.add(studentUsernameLabel, gbc);

        gbc.gridx = 1;
        formPanel.add(studentComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel courseNameLabel = new JLabel("Kurs auswählen");
        formPanel.add(courseNameLabel, gbc);

        gbc.gridx = 1;
        formPanel.add(courseComboBox, gbc);

        //Speichern - Button
        gbc.gridy = 2;
        saveButton = new JButton("Speichern");
        saveButton.addActionListener(this);
        formPanel.add(saveButton, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Zurück - Button
        backButton = new JButton("Zurück");
        backButton.addActionListener(this);
        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        southPanel.add(backButton);
        add(southPanel, BorderLayout.SOUTH);

        refreshData();

    }

    @Override
    public void refreshData() {
        loadData();
        resetForm();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveButton) {
            User selectedStudent = (User) studentComboBox.getSelectedItem();
            Course selectedCourse = (Course) courseComboBox.getSelectedItem();

            if (selectedStudent == null || selectedCourse == null) {
                JOptionPane.showMessageDialog(mainApp, "Bitte einen Schüler und einen Kurs auswählen.", "Fehler", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {

            if (enrollmentDataAccess.checkEnrollment(selectedStudent.getId(), selectedCourse.getId())) {
                JOptionPane.showMessageDialog(mainApp, "Der Schüler ist schon in dem Kurs eingeschrieben", "Hinweis", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            if (enrollmentDataAccess.enrollStudent(selectedStudent.getId(), selectedCourse.getId())) {
                JOptionPane.showMessageDialog(mainApp, String.format("%s wurde erfolgreich in den Kurs %s eingeschrieben", selectedStudent.getFirstName() + " " + selectedStudent.getLastName(), selectedCourse.getName()));
            } else {
                JOptionPane.showMessageDialog(mainApp, "Fehler beim Einschreiben des Schülers in den Kurs.", "Fehler", JOptionPane.ERROR_MESSAGE);
            }

            // Felder und ComboBox-Inhalt erneuern
            refreshData();

            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(mainApp, "Fehler beim Einschreiben des Schülers in den Kurs.", "Fehler", JOptionPane.ERROR_MESSAGE);
            }

        } else if (e.getSource() == backButton) {
            mainApp.showPanel(App.ADMIN_DASHBOARD_PANEL);
        }
    }

    private void resetForm() {
        studentComboBox.setSelectedIndex(-1);
        courseComboBox.setSelectedIndex(-1);
    }

    private void loadData() {
        // Alle Schüler in die ComboBox
        studentComboBox.removeAllItems();
        ArrayList<User> students = userDataAccess.findUsersByRole("student");
        for (User student : students) {
            studentComboBox.addItem(student);
        }
        // Alle Kurse in die ComboBox
        courseComboBox.removeAllItems();
        ArrayList<Course> courses = courseDataAccess.findAllCourses();
        for (Course course: courses) {
            courseComboBox.addItem(course);
        }
    }
}
