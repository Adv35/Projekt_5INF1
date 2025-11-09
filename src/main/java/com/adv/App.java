package com.adv;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The main application window (JFrame).
 * It uses a CardLayout to switch between different panels (views).
 */
public class App extends JFrame implements ActionListener {

    private CardLayout cardLayout;
    private JLayeredPane layeredPane;
    private JPanel mainPanel;

    // SidePanel und der Button zum SidePanel
    private JButton menuButton;
    private SideMenuPanel sideMenuPanel;

    // Panel names for the CardLayout
    public static final String LOGIN_PANEL = "LoginPanel";
    // We will add more panel names here later (e.g., "StudentDashboard", "AdminDashboard")

    public App() {
        setTitle("Notenverwaltung");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        // JLayeredPane ermöglicht es, Komponenten übereinander zu legen
        layeredPane = new JLayeredPane();
        setContentPane(layeredPane);


        // --- Menu Button ---
        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.LEADING));
        menuButton = new JButton("☰");
        menuButton.setBorderPainted(false);
        menuButton.setFocusPainted(false);
        menuButton.setFont(new Font("Segue UI Symbol", Font.PLAIN, 30));
        menuButton.setBackground(Color.lightGray);
        menuButton.addActionListener(this);
        topBar.add(menuButton);

        // Die Top-Bar liegt auf der untersten Ebene und bekommt eine feste Position und Größe
        topBar.setBounds(0, 0, 800, 60);
        layeredPane.add(topBar, JLayeredPane.DEFAULT_LAYER);

        // Seitenleistenmenü erstellen
        sideMenuPanel = new SideMenuPanel(this);

        // Set up the CardLayout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        // Das Hauptpanel (mit dem späteren Login) füllt den gesamten Bereich unter der Top-Bar
        mainPanel.setBounds(0, 0, 800, 600);
        layeredPane.add(mainPanel, JLayeredPane.DEFAULT_LAYER);

        // Create the panels and add them to the CardLayout
        //LoginPanel loginPanel = new LoginPanel(this); // Pass a reference to this frame
        //mainPanel.add(loginPanel, LOGIN_PANEL);
        // Add other panels here later
        // mainPanel.add(new StudentDashboardPanel(), "StudentDashboard");


        LoginPanel loginPanel = new LoginPanel(this);
        mainPanel.add(loginPanel, LOGIN_PANEL);

        // Die Seitenleiste liegt auf einer höheren Ebene und ist anfangs unsichtbar
        sideMenuPanel.setBounds(0, 0, sideMenuPanel.getPreferredSize().width, 600);
        layeredPane.add(sideMenuPanel, JLayeredPane.PALETTE_LAYER);
        sideMenuPanel.setVisible(false);

        // Show the login panel first
        showPanel(LOGIN_PANEL);
    }

    /**
     * Switches the visible panel in the CardLayout.
     * @param panelName The name of the panel to show.
     */
    public void showPanel(String panelName) {
        cardLayout.show(mainPanel, panelName);
    }

    // zeigt Seitenleiste an
    public void showSideMenu() {
        sideMenuPanel.setVisible(true);
    }

    public void hideSideMenu() {
        sideMenuPanel.setVisible(false);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == menuButton) {
            // Der Sandwich-Button zeigt das Menü immer an. Geschlossen wird es nur über das "X" im Panel.
            showSideMenu();
        }
    }
}