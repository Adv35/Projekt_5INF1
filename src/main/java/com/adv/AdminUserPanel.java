package com.adv;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class AdminUserPanel extends CommonJPanel implements ActionListener {

    private App mainApp;
    private UserDataAccess userDataAccess;
    private PasswordManagement passwordManagement;

    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;
    private JButton saveButton;
    private JButton backButton;

    public AdminUserPanel(App mainApp) {
        this.mainApp = mainApp;
        this.userDataAccess = new UserDataAccess();
        this.passwordManagement = new PasswordManagement();

        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(20, 20, 20, 20));

        // --- TITEL ---
        JLabel titleLable = new JLabel("Neuen Benutzer erstellen", SwingConstants.CENTER);
        titleLable.setFont(new Font("Segoe UI", Font.BOLD, 24));
        add(titleLable, BorderLayout.NORTH);

        // --- Formular-Panel ---
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Felder initialisieren
        firstNameField = new JTextField(20);
        lastNameField  = new JTextField(20);
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);

        String[] roles = new String[] {"student", "teacher", "admin"};
        roleComboBox = new JComboBox<>(roles);

        // Felder (und die zugehörigen Labels) ins Panel hinzufügen
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel firstNameLabel = new JLabel("Vorname:");
        formPanel.add(firstNameLabel, gbc);

        gbc.gridx = 1;
        formPanel.add(firstNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel lastNameLabel = new JLabel("Nachname:");
        formPanel.add(lastNameLabel, gbc);

        gbc.gridx = 1;
        formPanel.add(lastNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel usernameLabel = new JLabel("Benutzername:");
        formPanel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        formPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel passwordLabel = new JLabel("Passwort:");
        formPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        formPanel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        JLabel rolePanel = new JLabel("Rolle");
        formPanel.add(rolePanel, gbc);

        gbc.gridx = 1;
        formPanel.add(roleComboBox, gbc);

        gbc.gridy = 5;
        saveButton = new JButton("Speichern");
        saveButton.addActionListener(this);
        formPanel.add(saveButton, gbc);

        // "Wrapper" - Panel (einfach um alles schön zu zentrieren)
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.add(formPanel);
        add(centerPanel, BorderLayout.CENTER);

        // --- Zurück Button ---
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
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String role = (String) roleComboBox.getSelectedItem();

            if (firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() || password.isEmpty() || role.isEmpty()) {
                JOptionPane.showMessageDialog(mainApp, "Bitte alle Felder ausfüllen", "Fehler", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String hashedPassword = passwordManagement.hashPassword(password);
            User newUser = new User(firstName, lastName, username, hashedPassword, role);

            if(userDataAccess.createUser(newUser)) {
                JOptionPane.showMessageDialog(mainApp, String.format("Benutzer %s erfolgreich erstellt.", username));
                // Felder leeren für nächste Eingabe
                resetForm();
            } else {
                JOptionPane.showMessageDialog(mainApp, "Fehler beim Erstellen des Benutzers. Versuchen Sie einen anderen Benutzernamen", "Fehler", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == backButton) {
            resetForm();
            mainApp.showPanel(App.ADMIN_DASHBOARD_PANEL);
        }
    }

    // Leert alle Felder
    private void resetForm() {
        firstNameField.setText(null);
        lastNameField.setText(null);
        usernameField.setText(null);
        passwordField.setText(null);
        roleComboBox.setSelectedIndex(-1);
    }
}
