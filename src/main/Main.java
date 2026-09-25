package main;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import ui.AdminDashboard;
import ui.Dashboard;
import ui.LoginGUI;
import ui.Manage;

public class Main {
    public static void main(String[] args) {
         SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Course Registration - Login");
            frame.setContentPane(new LoginGUI());
            frame.pack();
            frame.setResizable(false);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
