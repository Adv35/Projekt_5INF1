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
        GridBagConstraints gbc = new GridBagConstraints();

        // 5 in jeder Richtung Abstand, für Ästhetik
        gbc.insets = new Insets(5, 5, 5, 5);


        // --- UI Komponenten erstellen ---
        JLabel usernameLabel = new JLabel("Benutzername:");
        usernameField = new JTextField(20);
        JLabel passwordLabel = new JLabel("Passwort:");
        passwordField = new JPasswordField(20);
        loginButton = new JButton("Einloggen");
        loginButton.addActionListener(this);

        // --- UI Elemente anordnen ---
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(usernameLabel, gbc);

        gbc.gridx = 1;
        add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(passwordLabel, gbc);

        gbc.gridx = 1;
        add(passwordField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        add(loginButton, gbc);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            User user = userDataAccess.findUserByUsername(username);
            if (user != null && passwordManagement.checkPassword(password, user.getPasswordHash())) {
                JOptionPane.showMessageDialog(mainApp, "Login erfolgreich! Willkommen, " + user.getFirstName());
            } else {
                JOptionPane.showMessageDialog(mainApp, "Login fehlgeschlagen. Bitte versuchen Sie es erneut.");
            }

        }
    }






}
