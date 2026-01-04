package com.adv;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminPasswordResetPanel extends CommonJPanel implements ActionListener {

    private App mainApp;
    private UserDataAccess userDataAccess;
    private PasswordManagement passwordManagement;

    private JTextField usernameField;
    private JPasswordField newPasswordField;
    private JButton saveButton;
    private JButton backButton;

    public AdminPasswordResetPanel(App mainApp) {
        this.mainApp = mainApp;
        this.userDataAccess = new UserDataAccess();
        this.passwordManagement = new PasswordManagement();

        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(20,20,20,20));

        // --- Titel ---
        JLabel titleLabel = new JLabel("Passwort zurücksetzen", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        // --- Formular Panel ---
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // --- Felder initialisieren ---
        usernameField = new JTextField(20);
        newPasswordField = new JPasswordField(20);

        // Felder positionieren
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel usernameLabel = new JLabel("Benutzername:");
        formPanel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        formPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel newPasswordLabel = new JLabel("Neues Passwort:");
        formPanel.add(newPasswordLabel, gbc);

        gbc.gridx = 1;
        formPanel.add(newPasswordField, gbc);

        // Speichern Button
        gbc.gridy = 2;
        saveButton = new JButton("Speichern");
        saveButton.addActionListener(this);
        formPanel.add(saveButton, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Zurück Button
        backButton = new JButton("Zurück");
        backButton.addActionListener(this);
        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        southPanel.add(backButton);
        add(southPanel, BorderLayout.SOUTH);

        resetForm();
    }

    @Override
    public void refreshData() {
        resetForm();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveButton) {
            String username = usernameField.getText();
            String newPassword = new String(newPasswordField.getPassword());

            User user = userDataAccess.findUserByUsername(username);

            if (user == null) {
                JOptionPane.showMessageDialog(mainApp, "Der Nutzername ist falsch oder existiert nicht.", "Hinweis.", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            String hashedPassword = passwordManagement.hashPassword(newPassword);
            if(userDataAccess.updatePassword(user.getId(), hashedPassword)) {
                JOptionPane.showMessageDialog(mainApp, String.format("'%s' ist das neue Passwort von %s. ", newPassword, username));
            }
            resetForm();
        } else if (e.getSource() == backButton) {
            resetForm();
            mainApp.showPanel(App.ADMIN_DASHBOARD_PANEL);
        }
    }

    private void resetForm() {
        usernameField.setText(null);
        newPasswordField.setText(null);
    }
}
