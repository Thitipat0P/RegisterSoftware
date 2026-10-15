package main;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import ui.LoginGUI;

/** จุดเริ่มต้นของโปรแกรม: เปิดหน้าต่างหลักพร้อมหน้า Login */
public class Main {
    public static void main(String[] args) {
        // สร้างและแสดง GUI บน Event Dispatch Thread ตามหลักของ Swing
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Course Registration - Login");
            frame.setContentPane(new LoginGUI());  // เริ่มที่หน้า Login
            frame.pack();                          // ปรับขนาดตาม preferredSize ของหน้า
            frame.setResizable(false);             // ห้ามย่อ/ขยายหน้าต่าง (ใช้ layout แบบกำหนดพิกัด)
            frame.setLocationRelativeTo(null);     // แสดงกลางหน้าจอ
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
