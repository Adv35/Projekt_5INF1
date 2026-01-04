package com.adv;

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


    private LoginPanel loginPanel;

    private StudentDashboardPanel studentDashboardPanel;
    private StudentCourseDetailPanel studentCourseDetailPanel;

    private TeacherDashboardPanel teacherDashboardPanel;
    private TeacherCoursePanel teacherCoursePanel;
    private TeacherGradingPanel teacherGradingPanel;

    private AdminDashboardPanel adminDashboardPanel;
    private AdminUserPanel adminUserPanel;
    private AdminCoursePanel adminCoursePanel;
    private AdminEnrollmentPanel adminEnrollmentPanel;
    private AdminPasswordResetPanel adminPasswordResetPanel;


    // Panel Namen für das CardLayout
    public static final String LOGIN_PANEL = "LoginPanel";
    public static final String STUDENT_DASHBOARD_PANEL = "StudentDashboardPanel";
    public static final String STUDENT_COURSE_DETAIL_PANEL = "StudentCourseDetailPanel";
    public static final String TEACHER_DASHBOARD_PANEL = "TeacherDashboardPanel";
    public static final String TEACHER_COURSE_PANEL = "TeacherCoursePanel";
    public static final String TEACHER_GRADING_PANEL = "TeacherGradingPanel";
    public static final String ADMIN_DASHBOARD_PANEL = "AdminDashboardPanel";
    public static final String ADMIN_USER_PANEL = "AdminUserPanel";
    public static final String ADMIN_COURSE_PANEL = "AdminCoursePanel";
    public static final String ADMIN_ENROLLMENT_PANEL = "AdminEnrollmentPanel";
    public static final String ADMIN_PASSWORD_RESET_PANEL = "AdminPasswordResetPanel";

    private String currentPanelName;

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
        layeredPane.add(topBar, Integer.valueOf(2));

        // Seitenleistenmenü erstellen
        sideMenuPanel = new SideMenuPanel(this);

        // CardLayout erstellen
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        // Das Hauptpanel füllt den gesamten Bereich unter der Top-Bar
        mainPanel.setBounds(0, 60, 800, 500);
        layeredPane.add(mainPanel, Integer.valueOf(1));

        loginPanel = new LoginPanel(this);
        mainPanel.add(loginPanel, LOGIN_PANEL);

        studentDashboardPanel = new StudentDashboardPanel(this);
        mainPanel.add(studentDashboardPanel, STUDENT_DASHBOARD_PANEL);

        studentCourseDetailPanel = new StudentCourseDetailPanel(this);
        mainPanel.add(studentCourseDetailPanel, STUDENT_COURSE_DETAIL_PANEL);

        teacherDashboardPanel = new TeacherDashboardPanel(this);
        mainPanel.add(teacherDashboardPanel, TEACHER_DASHBOARD_PANEL);

        teacherCoursePanel = new TeacherCoursePanel(this);
        mainPanel.add(teacherCoursePanel, TEACHER_COURSE_PANEL);

        teacherGradingPanel = new TeacherGradingPanel(this);
        mainPanel.add(teacherGradingPanel, TEACHER_GRADING_PANEL);

        adminDashboardPanel = new AdminDashboardPanel(this);
        mainPanel.add(adminDashboardPanel, ADMIN_DASHBOARD_PANEL);

        adminUserPanel = new AdminUserPanel(this);
        mainPanel.add(adminUserPanel, ADMIN_USER_PANEL);

        adminCoursePanel = new AdminCoursePanel(this);
        mainPanel.add(adminCoursePanel, ADMIN_COURSE_PANEL);

        adminEnrollmentPanel = new AdminEnrollmentPanel(this);
        mainPanel.add(adminEnrollmentPanel, ADMIN_ENROLLMENT_PANEL);

        adminPasswordResetPanel = new AdminPasswordResetPanel(this);
        mainPanel.add(adminPasswordResetPanel, ADMIN_PASSWORD_RESET_PANEL);

        // Die Seitenleiste liegt auf einer höheren Ebene und ist anfangs unsichtbar
        sideMenuPanel.setBounds(0, 0, sideMenuPanel.getPreferredSize().width, 600);
        layeredPane.add(sideMenuPanel, JLayeredPane.PALETTE_LAYER);
        sideMenuPanel.setVisible(false);

        showPanel(LOGIN_PANEL);
    }

    // --- GETTER ---
    public StudentDashboardPanel getStudentDashboardPanel() {
        return studentDashboardPanel;
    }

    public StudentCourseDetailPanel getStudentCourseDetailPanel() {
        return studentCourseDetailPanel;
    }

    public TeacherDashboardPanel getTeacherDashboardPanel() {
        return teacherDashboardPanel;
    }

    public TeacherCoursePanel getTeacherCoursePanel() {
        return teacherCoursePanel;
    }

    public TeacherGradingPanel getTeacherGradingPanel() {
        return teacherGradingPanel;
    }

    public AdminDashboardPanel getAdminDashboardPanel() {
        return adminDashboardPanel;
    }

    public AdminUserPanel getAdminUserPanel() {
        return adminUserPanel;
    }

    public AdminCoursePanel getAdminCoursePanel() {
        return adminCoursePanel;
    }

    public AdminEnrollmentPanel getAdminEnrollmentPanel() {
        return adminEnrollmentPanel;
    }

    public AdminPasswordResetPanel getAdminPasswordResetPanel() {
        return adminPasswordResetPanel;
    }


    public void showPanel(String panelName) {
        this.currentPanelName = panelName;
        cardLayout.show(mainPanel, panelName);
    }

    // zeigt Seitenleiste an
    public void showSideMenu() {
        sideMenuPanel.setVisible(true);
    }

    // versteckt Seitenleiste
    public void hideSideMenu() {
        sideMenuPanel.setVisible(false);
    }

    public void logout() {
        hideSideMenu();
        showPanel(LOGIN_PANEL);
    }

    public void refresh() {
        hideSideMenu();
        if (currentPanelName.equals(TEACHER_DASHBOARD_PANEL)) {
            teacherDashboardPanel.refreshData();
        } else if (currentPanelName.equals(TEACHER_COURSE_PANEL)) {
            teacherCoursePanel.refreshData();
        } else if (currentPanelName.equals(STUDENT_DASHBOARD_PANEL)) {
            studentDashboardPanel.refreshData();
        } else if (currentPanelName.equals(STUDENT_COURSE_DETAIL_PANEL)) {
            studentCourseDetailPanel.refreshData();
        } else if (currentPanelName.equals(TEACHER_GRADING_PANEL)) {
            teacherGradingPanel.refreshData();
        } else if (currentPanelName.equals(ADMIN_DASHBOARD_PANEL)) {
            adminDashboardPanel.refreshData();
        } else if (currentPanelName.equals(ADMIN_USER_PANEL)) {
            adminUserPanel.refreshData();
        } else if (currentPanelName.equals(ADMIN_COURSE_PANEL)) {
            adminCoursePanel.refreshData();
        } else if (currentPanelName.equals(ADMIN_PASSWORD_RESET_PANEL)) {
            adminPasswordResetPanel.refreshData();
        }

        revalidate();
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == menuButton) {
            // Der Sandwich-Button zeigt das Menü immer an. Geschlossen wird es nur über das "X" im Panel.
            showSideMenu();
        }
    }
}