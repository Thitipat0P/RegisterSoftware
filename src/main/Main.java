package main;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;



import ui.LoginGUI;
import ui.StudentDashboard;

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
