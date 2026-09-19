package ui;  

import service.AuthenticationService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI {

    private AuthenticationService authService;

    public LoginGUI() {
        authService = new AuthenticationService();
        setupUI();
    }

    private void setupUI() {
        JFrame frame = new JFrame("Course Registration - Login");
        frame.setSize(300, 200);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // ให้อยู่กึ่งกลางหน้าจอ

        JLabel userLabel = new JLabel("User ID:");
        userLabel.setBounds(30, 30, 80, 25);
        frame.add(userLabel);

        JTextField userText = new JTextField(20);
        userText.setBounds(100, 30, 150, 25);
        frame.add(userText);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(30, 65, 80, 25);
        frame.add(passLabel);

        JPasswordField passText = new JPasswordField(20);
        passText.setBounds(100, 65, 150, 25);
        frame.add(passText);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(100, 110, 100, 25);
        frame.add(loginButton);

        // เมื่อกดปุ่ม Login
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user = userText.getText();
                String pass = new String(passText.getPassword());

                // ส่งข้อมูลไปให้ Service เช็คในไฟล์ CSV
                String role = authService.login(user, pass);

                if (role != null) {
                    frame.dispose(); // ปิดหน้าต่าง Login
                    
                    // เช็คเงื่อนไขเพื่อเปิดหน้าจอแยกตาม Role
                    if (role.equals("ADMIN")) {
                        new AdminDashboard();
                    } else if (role.equals("INSTRUCTOR")) {
                        new InstructorDashboard();
                    } else if (role.equals("STUDENT")) {
                        new StudentDashboard();
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "ID หรือ Password ไม่ถูกต้อง!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        frame.setVisible(true);
    }

    // จุดเริ่มต้นรันโปรแกรม
    public static void main(String[] args) {
        new LoginGUI(); 
    }
}