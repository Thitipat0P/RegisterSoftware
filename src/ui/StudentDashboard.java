package ui;
import javax.swing.*;

public class StudentDashboard {
    public StudentDashboard() {
        JFrame frame = new JFrame("Student Dashboard");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        
        JLabel label = new JLabel("ยินดีต้อนรับ นักศึกษา (Student)", SwingConstants.CENTER);
        frame.add(label);
        
        frame.setVisible(true);
    }
}