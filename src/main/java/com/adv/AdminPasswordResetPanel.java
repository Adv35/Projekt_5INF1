package com.adv;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Das Panel was erscheint, wenn der Admin in AdminDashboard die Option Benutzer zurücksetzen gedrückt hat.
 * Bietet ein Formular, über den der Admin das Passwort von beliebigen Nutzern ändern kann.
 * @author Advik Vattamwar
 * @version 10.01.2026
 * **/
public class AdminPasswordResetPanel extends CommonJPanel implements ActionListener {

    // Das Hauptobjekt / Steuerobjekt von App.java
    private App mainApp;
    private UserDataAccess userDataAccess;
    private PasswordManagement passwordManagement;
    private User currentAdmin;

    private JTextField usernameField;
    private JPasswordField newPasswordField;
    private JButton saveButton;
    private JButton backButton;

    /**
     *  Konstruktor des Panels.
     *  Baut das Formular mit seinem TextFeldern, Buttons etc.
     * @param mainApp - Das Hauptpanel
     * **/
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

        // Alles zurücksetzen
        resetForm();
    }

    /** Setzt den angemeldeten Admin (currentAdmin) auf den Parameter und setzt Formular zurück.
     * @param admin Der übergebene Admin, welcher eingeloggt ist.
     * **/
    public void loadAdminData(User admin) {
        this.currentAdmin = admin;
        resetForm();
    }

    /** Setzt die Formularkomponenten zurück.
     * Implementiert, damit es von CommonJPanel erben kann (falls in späterer Implementierung benötigt)
     * **/
    @Override
    public void refreshData() {
        resetForm();
    }

    /** Methode implementiert von dem Interface Actionlistener.
     * Handling von Backend PasswortManagment und Zurückgehen zum Dashboard des Admins.
     * @param e Das ActionEvent, das die Buttons zum ActionListener geben.
     * **/
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveButton) {
            // Daten aus Formularkomponenten holen
            String username = usernameField.getText();
            String newPassword = new String(newPasswordField.getPassword());

            // Nutzer holen
            User user = userDataAccess.findUserByUsername(username);

            if (user == null) {
                JOptionPane.showMessageDialog(mainApp, "Der Nutzername ist falsch oder existiert nicht.", "Hinweis.", JOptionPane.INFORMATION_MESSAGE);
                return;
                // Wichtiges Security Feature:
                // Falls Admin Rechner anlässt oder so, dass er sein eigenes Passwort nicht ohne das Alte zu kennen selber ändern kann
            } else if (user.getId().equals(this.currentAdmin.getId())) {
                JOptionPane.showMessageDialog(mainApp, "Um ihr eigenes Passwort zu ändern, gehen Sie bitte ins Menü." , "Hinweis", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            String hashedPassword = passwordManagement.hashPassword(newPassword);

            //Passwort neusetzen
            if(userDataAccess.updatePassword(user.getId(), hashedPassword)) {
                JOptionPane.showMessageDialog(mainApp, String.format("'%s' ist das neue Passwort von %s. ", newPassword, username));
            }

            resetForm();

        } else if (e.getSource() == backButton) {
            resetForm();
            mainApp.showPanel(App.ADMIN_DASHBOARD_PANEL);
        }
    }

    /** Setzt die Formularkomponenten zurück.
     * **/
    private void resetForm() {
        usernameField.setText(null);
        newPasswordField.setText(null);
    }
}
