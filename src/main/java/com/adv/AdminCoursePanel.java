package com.adv;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AdminCoursePanel extends CommonJPanel implements ActionListener {

    private App mainApp;
    private CourseDataAccess courseDataAccess;
    private UserDataAccess userDataAccess;

    // Formular - Komponenten
    private JTextField courseNameField;
    private JTextArea descriptionArea;
    private JComboBox<User> teacherComboBox;
    private JButton saveButton;
    private JButton backButton;

    public AdminCoursePanel(App mainApp) {
        this.mainApp = mainApp;
        this.courseDataAccess = new CourseDataAccess();
        this.userDataAccess = new UserDataAccess();

        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(20, 20, 20, 20));

        // --- Titel ---
        JLabel titleLabel = new JLabel("Neuen Kurs erstellen", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        // --- Formular Panel ---
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Felder initialisieren
        this.courseNameField = new JTextField(20);
        this.descriptionArea = new JTextArea(5, 20);
        this.descriptionArea.setPreferredSize(descriptionArea.getPreferredSize());
        this.teacherComboBox = new JComboBox<>();
        this.teacherComboBox.setRenderer(new UserComboBoxRenderer());

        // Felder zum Panel hinzufügen
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel courseNameLabel = new JLabel("Kursname:");
        formPanel.add(courseNameLabel, gbc);

        gbc.gridx = 1;
        formPanel.add(courseNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel descriptionLabel = new JLabel("Beschreibung:");
        formPanel.add(descriptionLabel, gbc);

        gbc.gridx = 1;
        formPanel.add(descriptionArea, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel teacherSelectLabel = new JLabel("Lehrkraft:");
        formPanel.add(teacherSelectLabel, gbc);

        gbc.gridx = 1;
        formPanel.add(teacherComboBox, gbc);

        gbc.gridy = 3;
        saveButton = new JButton("Speichern");
        saveButton.addActionListener(this);
        formPanel.add(saveButton, gbc);

        add(formPanel, BorderLayout.CENTER);

        // --- Zurück-Button ---
        backButton = new JButton("Zurück");
        backButton.addActionListener(this);
        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        southPanel.add(backButton);
        add(southPanel, BorderLayout.SOUTH);

        refreshData();
    }

    @Override
    public void refreshData() {
        teacherComboBox.removeAllItems();
        ArrayList<User> teachers = userDataAccess.findUsersByRole("teacher");
        for (User teacher : teachers) {
            teacherComboBox.addItem(teacher);
        }
        resetForm();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveButton) {
            String courseName = courseNameField.getText();
            String description = descriptionArea.getText();
            User selectedTeacher = (User) teacherComboBox.getSelectedItem();

            if (courseName.isEmpty() || selectedTeacher == null) {
                JOptionPane.showMessageDialog(mainApp, "Bitte Kursname und Lehrer auswählen.", "Fehler", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Course newCourse = new Course(courseName, selectedTeacher.getId(), description);
            if (courseDataAccess.createCourse(newCourse)) {
                JOptionPane.showMessageDialog(mainApp, String.format("Kurs %s erfolgreich erstellt", courseName));
                resetForm();
            } else {
                JOptionPane.showMessageDialog(mainApp, "Fehler beim Erstellen des Kurses. \n Möglicherweise gibt es schon einen Kurs mit demselben Namen?", "Fehler", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == backButton) {
            resetForm();
            mainApp.showPanel(App.ADMIN_DASHBOARD_PANEL);
        }
    }

    private void resetForm() {
        courseNameField.setText(null);
        descriptionArea.setText(null);
        teacherComboBox.setSelectedIndex(-1);
    }


}
