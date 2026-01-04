package com.adv;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPanel extends JPanel implements ActionListener {

    private App mainApp;    // Assoziation auf HauptFrame, zum Wechseln der Panels

    // --- Backend Instanzen für Login ---
    private UserDataAccess userDataAccess;
    private PasswordManagement passwordManagement;

    // --- UI Elemente ---
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginPanel(App mainApp) {
        this.mainApp = mainApp;
        this.userDataAccess = new UserDataAccess();
        this.passwordManagement = new PasswordManagement();

        setLayout(new GridBagLayout());

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // 10 in jeder Richtung Abstand, für Ästhetik
        gbc.insets = new Insets(10, 10, 10, 10);


        // --- UI Komponenten in formPanel erstellen ---
        JLabel usernameLabel = new JLabel("Benutzername:");
        usernameLabel.setPreferredSize(new Dimension(80, 10));
        usernameField = new JTextField(20);
        JLabel passwordLabel = new JLabel("Passwort:");
        passwordField = new JPasswordField(20);
        loginButton = new JButton("Einloggen");
        loginButton.addActionListener(this);

        // --- UI Elemente anordnen ---

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        formPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        formPanel.add(passwordField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 1;
        formPanel.add(loginButton, gbc);

        add(formPanel);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            User user = userDataAccess.findUserByUsername(username);
            if (user != null && passwordManagement.checkPassword(password, user.getPasswordHash())) {
                JOptionPane.showMessageDialog(mainApp, "Login erfolgreich! Willkommen, " + user.getFirstName());
                usernameField.setText(null);
                passwordField.setText(null);
                if (user.getRole().equals("student")) {
                    StudentDashboardPanel studentDashboardPanel = mainApp.getStudentDashboardPanel();
                    studentDashboardPanel.loadStudentData(user);
                    mainApp.showPanel(App.STUDENT_DASHBOARD_PANEL);
                } else if (user.getRole().equals("teacher")) {
                    TeacherDashboardPanel teacherDashboardPanel = mainApp.getTeacherDashboardPanel();
                    teacherDashboardPanel.loadTeacherData(user);
                    mainApp.showPanel(App.TEACHER_DASHBOARD_PANEL);
                } else if (user.getRole().equals("admin")) {
                    AdminDashboardPanel adminDashboardPanel = mainApp.getAdminDashboardPanel();
                    adminDashboardPanel.loadAdminData(user);
                    mainApp.showPanel(App.ADMIN_DASHBOARD_PANEL);
                } else {
                    JOptionPane.showMessageDialog(mainApp, "Login für Ihre Rolle nicht möglich. " +
                            "Bitte wenden Sie sich an den IT-Support!");
                }

            } else {
                JOptionPane.showMessageDialog(mainApp, "Login fehlgeschlagen. Bitte versuchen Sie es erneut.");
            }

        }
    }






}
