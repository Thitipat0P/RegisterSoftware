package ui;
import javax.swing.*;

public class InstructorDashboard {
    public InstructorDashboard() {
        JFrame frame = new JFrame("Instructor Dashboard");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        
        JLabel label = new JLabel("ยินดีต้อนรับ อาจารย์ผู้สอน (Instructor)", SwingConstants.CENTER);
        frame.add(label);
        
        frame.setVisible(true);
    }
}