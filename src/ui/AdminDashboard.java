package ui;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;

import model.Admin;

public class AdminDashboard extends JPanel {

    public AdminDashboard(Admin admin) {
        setBackground(new Color(236, 238, 248));
        setLayout(null);
        setPreferredSize(new Dimension(970, 600));
    }
}