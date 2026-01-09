package com.adv;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserPasswordResetPanel extends CommonJPanel implements ActionListener {

    private App mainApp;
    private PasswordManagement passwordManagement;
    private UserDataAccess userDataAccess;
    private User user;

    private JPasswordField oldPasswordField;
    private JPasswordField newPasswordField;
    private JButton saveButton;
    private JButton backButton;

    public UserPasswordResetPanel(App mainApp) {
        this.mainApp = mainApp;
        this.passwordManagement = new PasswordManagement();
        this.userDataAccess = new UserDataAccess();

        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(20, 20, 20, 20));

        // --- TITEL ---
        JLabel titleLable = new JLabel("Passwort ändern", SwingConstants.CENTER);
        titleLable.setFont(new Font("Segoe UI", Font.BOLD, 24));
        add(titleLable, BorderLayout.NORTH);

        // --- Formular-Panel ---
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // --- FELDER INITIALISIEREN ---
        this.oldPasswordField = new JPasswordField(20);
        this.newPasswordField = new JPasswordField(20);

        // Felder (und die zugehörigen Labels) ins Panel hinzufügen
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel oldPasswordLabel = new JLabel("Jetziges Passwort:");
        formPanel.add(oldPasswordLabel, gbc);

        gbc.gridx = 1;
        formPanel.add(oldPasswordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel newPasswordLabel = new JLabel("Neues Passwort:");
        formPanel.add(newPasswordLabel, gbc);

        gbc.gridx = 1;
        formPanel.add(newPasswordField, gbc);

        gbc.gridy = 2;

        // save - Button
        this.saveButton = new JButton("Speichern");
        this.saveButton.addActionListener(this);
        formPanel.add(this.saveButton, gbc);

        add(formPanel, BorderLayout.CENTER);

        // --- Zurück Button ---
        this.backButton = new JButton("Zurück");
        this.backButton.addActionListener(this);
        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        southPanel.add(this.backButton);
        add(southPanel, BorderLayout.SOUTH);

        refreshData();
    }

    public void loadUserData(User user) {
        this.user = user;
        refreshData();
    }


    /**
     *
     */
    @Override
    public void refreshData() {
        this.oldPasswordField.setText(null);
        this.newPasswordField.setText(null);
    }

    /**
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveButton) {
            String oldPassword = new String(oldPasswordField.getPassword());
            String newPassword = new String(newPasswordField.getPassword());

            if (passwordManagement.checkPassword(oldPassword, user.getPasswordHash())) {
                if (userDataAccess.updatePassword(user.getId(), passwordManagement.hashPassword(newPassword))) {
                    JOptionPane.showMessageDialog(mainApp, "Passwort erfolgreich geändert.", "Hinweis", JOptionPane.INFORMATION_MESSAGE);
                    refreshData();
                    mainApp.showPanel(App.LOGIN_PANEL);

                } else {
                    JOptionPane.showMessageDialog(mainApp, "Passwort ändern fehlgeschlagen.", "Fehler", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(mainApp, "Ihr jetziges Passwort ist falsch. Bitte versuchen Sie es erneut. \n " +
                        "Wenn Sie sich nicht an ihr Passwort erinnern können, kontaktieren Sie bitte einen Administrator", "Fehler", JOptionPane.ERROR_MESSAGE);
            }

        } else if (e.getSource() == backButton) {
            refreshData();
            if (user == null) {
                mainApp.showPanel(App.LOGIN_PANEL);
            } else if (user.getRole().equals("student")) {
                mainApp.showPanel(App.STUDENT_DASHBOARD_PANEL);
            } else if (user.getRole().equals("teacher")) {
                mainApp.showPanel(App.TEACHER_DASHBOARD_PANEL);
            } else if (user.getRole().equals("admin")) {
                mainApp.showPanel(App.ADMIN_DASHBOARD_PANEL);
            } else {
                mainApp.showPanel(App.LOGIN_PANEL);
            }
        }
    }
}
