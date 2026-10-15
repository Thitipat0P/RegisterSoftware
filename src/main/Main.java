package main;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import ui.LoginGUI;

/** จุดเริ่มต้นของโปรแกรม: เปิดหน้าต่างและแสดงหน้า Login */
public class Main {
    public static void main(String[] args) {
        // สร้างและแสดง GUI บน Event Dispatch Thread ตามมาตรฐานของ Swing
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Course Registration - Login");
            frame.setContentPane(new LoginGUI());                    // หน้าแรกคือหน้า Login
            frame.pack();                                            // ปรับขนาดหน้าต่างตาม preferredSize ของ panel
            frame.setResizable(false);                               // ไม่ให้ย่อ/ขยายหน้าต่าง
            frame.setLocationRelativeTo(null);                       // แสดงกลางจอ
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    // ปิดหน้าต่าง = จบโปรแกรม
            frame.setVisible(true);
        });
    }
}
