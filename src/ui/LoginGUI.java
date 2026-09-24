package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import service.AuthenticationService;

/**
 * หน้า Login ของระบบลงทะเบียนเรียน 
 * 
 * โครงสร้างหน้าจอแบ่งออกเป็น 2 ฝั่ง:
 *  - ฝั่งซ้าย (jPanel1)  : แสดงชื่อระบบ (Course Registration System) 
 *  - ฝั่งขวา (jPanel2)  : มีฟอร์มกรอก Student ID / Password และปุ่ม Login
 */

public class LoginGUI extends JPanel {
    // LOGIN ID PASSWORD check
    private AuthenticationService authService = new AuthenticationService();

    // ===================== ค่าคงที่กำหนดสไตล์และข้อความเริ่มต้น =====================
    
    // กำหนดโทนสีที่ใช้ในหน้าจอ
    private static final Color COLOR_BRAND_BG = new Color(37, 51, 91);         // สีน้ำเงินเข้มฝั่งซ้าย
    private static final Color COLOR_PAGE_BG = new Color(236, 238, 248);       // สีเทาอ่อนฝั่งขวา
    private static final Color COLOR_BUTTON_BG = new Color(76, 110, 245);      // สีฟ้าปุ่ม Login

    // ===================== ตัวแปร Component ของ Swing =====================

    // Components ฝั่งซ้าย (Branding Area)
    private JPanel jPanel1;
    private JLabel jLabel4; // ข้อความบรรทัดที่ 3: "System"
    private JLabel jLabel5; // ข้อความบรรทัดที่ 1: "Course"
    private JLabel jLabel6; // ข้อความบรรทัดที่ 2: "Registration"

    // Components ฝั่งขวา (Login Form Area)
    private JPanel jPanel2;
    private JLabel jLabel7; // ข้อความหัวข้อ "Password"
    private JLabel jLabel8; // ข้อความต้อนรับ "Sign in to continue"
    private JLabel jLabel9; // ข้อความหัวข้อ "Student ID"
    private JTextField usertext;  // ช่องกรอก Student ID
    private JPasswordField passtext; // ช่องกรอก Password 
    private JButton loginbutton;        // ปุ่ม Login

    /**
     * คอนสตรัคเตอร์สร้างหน้าจอ Login
     */
    public LoginGUI() {
        initComponents();
    }

    // ===================== สร้างและจัดวาง UI ทั้งหมด =====================

    private void initComponents() {
        // ใช้ Null Layout เพื่อกำหนดพิกัด x, y, width, height ได้เองอย่างอิสระ
        setLayout(null);
        setPreferredSize(new Dimension(970, 600));
        buildLeftBrandingPanel();
        buildRightLoginPanel();
    }

    /**
     * สร้างแผงฝั่งซ้าย (jPanel1): แสดงโลโก้ ชื่อระบบ
     */
    private void buildLeftBrandingPanel() {
        jPanel1 = new JPanel();
        jPanel1.setBackground(COLOR_BRAND_BG);
        jPanel1.setLayout(null);
        jPanel1.setBounds(0, 0, 510, 600);

        // แสดงข้อความ Course Registration System ที่ฝั่งซ้าย
        jLabel5 = createBrandLabel("Course", 80, 210, 250, 40);
        jLabel6 = createBrandLabel("Registration", 80, 240, 300, 60);
        jLabel4 = createBrandLabel("System", 80, 290, 250, 60);

        jPanel1.add(jLabel5);
        jPanel1.add(jLabel6);
        jPanel1.add(jLabel4);

        add(jPanel1);
    }

    /**
     * ตัวช่วยสร้าง Label สำหรับข้อความแบรนด์ดิ้งฝั่งซ้าย เพื่อลดการเขียนโค้ดซ้ำซ้อน
     */
    private JLabel createBrandLabel(String text, int x, int y, int width, int height) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 36));
        label.setForeground(Color.WHITE);
        label.setBounds(x, y, width, height);
        return label;
    }

    /**
     * สร้างแผงฝั่งขวา (jPanel2): ฟอร์ม Login กรอก Student ID และ Password
     */
    private void buildRightLoginPanel() {
        jPanel2 = new JPanel();
        jPanel2.setBackground(COLOR_PAGE_BG);
        jPanel2.setLayout(null);
        jPanel2.setBounds(510, 0, 460, 600);

        // ข้อความต้อนรับ "Sign in to continue"
        jLabel8 = new JLabel("Sign in to continue");
        jLabel8.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 22));
        jLabel8.setBounds(120, 170, 250, 30);
        jPanel2.add(jLabel8);

        // Header & Field: Student ID
        jLabel9 = new JLabel("ID");
        jLabel9.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 12));
        jLabel9.setBounds(120, 220, 140, 20);
        jPanel2.add(jLabel9);

        usertext = new JTextField();
        usertext.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        usertext.setBounds(120, 240, 230, 40);
        usertext.addActionListener(this::usertextActionPerformed);
        jPanel2.add(usertext);

        // Header & Field: Password
        jLabel7 = new JLabel("Password");
        jLabel7.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 12));
        jLabel7.setBounds(120, 300, 140, 20);
        jPanel2.add(jLabel7);

        passtext = new JPasswordField();
        passtext.setFont(new Font("Segoe UI", Font.PLAIN, 18)); 
        passtext.setBounds(120, 320, 230, 40);
        jPanel2.add(passtext);

        // ปุ่ม Login
        loginbutton = new JButton("Login");
        loginbutton.setBackground(COLOR_BUTTON_BG);
        loginbutton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loginbutton.setForeground(Color.WHITE);
        loginbutton.setFocusPainted(false);
        loginbutton.setBounds(160, 390, 140, 30);
        loginbutton.addActionListener(this::loginbuttonActionPerformed);
        add(jPanel2);
        jPanel2.add(loginbutton);

    }

    
    // ===================== Event Handlers & Helper Methods =====================

    private void usertextActionPerformed(ActionEvent evt) {
        // TODO: สามารถใส่ Logic ที่ต้องการให้ทำงานเมื่อกด Enter ในช่อง Student ID
    }

    private void loginbuttonActionPerformed(ActionEvent evt) {
        String studentId = usertext.getText();
    String password = new String(passtext.getPassword());

    // 2. ส่งค่าไปให้ authService ตรวจสอบกับไฟล์ CSV
    String role = authService.login(studentId, password);

    // 3. ตรวจสอบ Role ที่ได้กลับมา
    if (role != null) {
        // ปิดหน้าต่าง Login ปัจจุบัน
        java.awt.Window currentWindow = SwingUtilities.getWindowAncestor(this);
        if (currentWindow != null) {
            currentWindow.dispose();
        }
        
        // สร้างหน้าต่างใหม่
        JFrame targetFrame = new JFrame();
        targetFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        targetFrame.setResizable(false);
        
        // ตรวจสอบเงื่อนไขเพื่อเปิดหน้าจอแยกตาม Role
        if (role.trim().toUpperCase().equals("ADMIN")) {
            targetFrame.setTitle("Course Registration - Admin Dashboard");
            targetFrame.setContentPane(new Admin()); 
        } else if (role.trim().toUpperCase().equals("STUDENT")) {
            targetFrame.setTitle("Course Registration - Student Dashboard");
            targetFrame.setContentPane(new Student()); 
        }
        
        // จัดขนาดและแสดงหน้าต่างใหม่
        targetFrame.pack();
        targetFrame.setLocationRelativeTo(null);
        targetFrame.setVisible(true);
        
    } else {
        // กรณีค้นหาไม่เจอ หรือ รหัสผิด
        JOptionPane.showMessageDialog(this, "ID หรือ Password ไม่ถูกต้อง!", "Error", JOptionPane.ERROR_MESSAGE);
    }
    }

    

    // main

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
