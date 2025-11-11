package com.adv;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class SideMenuPanel extends JPanel implements ActionListener {
    private App mainApp;
    private JButton themeToggleButton;
    private JButton closeMenuButton;
    private JLabel menuLabel;

    private boolean isDarkMode = false;

    // Theme Toggle Button Icons
    private ImageIcon sunIcon;
    private ImageIcon moonIcon;

    public SideMenuPanel(App mainApp) {
        this.mainApp = mainApp;
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(200, 0));

        loadIcons(); // Lädt die Bilder noch vor Erstellung der UI

        // --- SidePanel oben, Schließen Button ---
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(100, 100, 100));

        JPanel closeButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        closeButtonPanel.setBackground(new Color(100, 100, 100));
        closeMenuButton = new JButton("X");
        closeMenuButton.setBorderPainted(false);
        closeMenuButton.setFocusPainted(false);
        closeMenuButton.addActionListener(this);
        closeButtonPanel.add(closeMenuButton);
        topPanel.add(closeButtonPanel, BorderLayout.EAST); // East = Rechts

        menuLabel = new JLabel("Menu");
        menuLabel.setFont(new Font("Helvetica", Font.PLAIN, 25));
        menuLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(menuLabel, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);


        // --- Mittlerer Bereich des Sidepanels für Options ---
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(new Color(128, 128, 128));

        themeToggleButton = new JButton(isDarkMode ? sunIcon : moonIcon);
        themeToggleButton.setBorderPainted(false);
        themeToggleButton.setFocusPainted(false);
        themeToggleButton.addActionListener(this);
        centerPanel.add(themeToggleButton);
        add(centerPanel, BorderLayout.CENTER);
    }

    private void loadIcons() {
        // Images Laden
        Image sunImg = new ImageIcon(getClass().getResource("/icons/sun.png")).getImage();
        Image moonImg = new ImageIcon(getClass().getResource("/icons/moon.png")).getImage();
        // Images Skalieren
        sunIcon = new ImageIcon(sunImg.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        moonIcon = new ImageIcon(moonImg.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == themeToggleButton) {
            isDarkMode = !isDarkMode;
            if (!isDarkMode) {
                try {
                    UIManager.setLookAndFeel(new FlatLightLaf());
                    themeToggleButton.setIcon(moonIcon);
                } catch (UnsupportedLookAndFeelException ex) {
                    System.err.println("LaF initialisierung Gescheitert (zu LightMode)");
                }
            } else {
                try {
                    UIManager.setLookAndFeel(new FlatDarkLaf());
                    themeToggleButton.setIcon(sunIcon);
                } catch (UnsupportedLookAndFeelException ex) {
                    System.err.println("LaF initialisierung Gescheitert (zu DarkMode)");
                }
            }
            SwingUtilities.updateComponentTreeUI(mainApp); // Zeichnet die ganze App neu

        } else if (e.getSource() == closeMenuButton) {
            mainApp.hideSideMenu();
        }

    }
}

