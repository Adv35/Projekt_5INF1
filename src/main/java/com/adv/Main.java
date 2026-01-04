package com.adv;

import javax.swing.*;
import com.formdev.flatlaf.FlatLightLaf;

public class Main {
    public static void main(String[] args) {
        FlatLightLaf.setup();
        // "SwingUtilities.invokeLater()" -> Best-Practice, um GUIs zu starten
        SwingUtilities.invokeLater(() -> {
            App app = new App();
            app.setVisible(true);
        });
    }
}