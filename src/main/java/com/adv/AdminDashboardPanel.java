package com.adv;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminDashboardPanel extends CommonJPanel implements ActionListener {

    private App mainApp;
    private JLabel welcomeLabel;
    private User currentAdmin;

    private JButton createUserButton;
    private JButton createCourseButton;
    private JButton enrollStudentButton;
    private JButton userPasswordResetButton;

    public AdminDashboardPanel(App mainApp) {
        this.mainApp = mainApp;

        setLayout(new BorderLayout(0, 50));
        setBorder(new EmptyBorder(20,20,20,20));

        // --- Kopfzeile ---
        welcomeLabel = new JLabel("Admin Dashboard", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        add(welcomeLabel, BorderLayout.NORTH);

        // --- Menü-Buttons ---
        JPanel menuPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        createUserButton = createMenuButton("Benutzer erstellen");
        menuPanel.add(createUserButton, gbc);

        gbc.gridy++;
        createCourseButton = createMenuButton("Kurs erstellen");
        menuPanel.add(createCourseButton, gbc);

        gbc.gridy++;
        enrollStudentButton = createMenuButton("Schüler einschreiben");
        menuPanel.add(enrollStudentButton, gbc);

        gbc.gridy++;
        userPasswordResetButton = createMenuButton("Benutzerpasswort zurücksetzen");
        menuPanel.add(userPasswordResetButton, gbc);

        add(menuPanel, BorderLayout.CENTER);
    }

    public void loadAdminData(User admin) {
        this.currentAdmin = admin;
        welcomeLabel.setText("Willkommen, Admin " + admin.getLastName());
    }


    @Override
    public void refreshData() {
        if (this.currentAdmin != null) {
            loadAdminData(this.currentAdmin);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == createUserButton) {
            mainApp.showPanel(App.ADMIN_USER_PANEL);
        } else if (e.getSource() == createCourseButton) {
            mainApp.showPanel(App.ADMIN_COURSE_PANEL);
        } else if (e.getSource() == enrollStudentButton) {
            mainApp.showPanel(App.ADMIN_ENROLLMENT_PANEL);
        } else if (e.getSource() == userPasswordResetButton) {
            mainApp.showPanel(App.ADMIN_PASSWORD_RESET_PANEL);
        }
    }

    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        button.setPreferredSize(new Dimension(300, 50));
        button.addActionListener(this);
        return button;
    }
}
