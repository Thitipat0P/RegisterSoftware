package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import controller.LoginController;
import service.AuthenticationService;

/**
 * หน้า Login ของระบบลงทะเบียนเรียน 
 * 
 * โครงสร้างหน้าจอแบ่งออกเป็น 2 ฝั่งหลัก:
 *  - ฝั่งซ้าย (jPanel1)  : พื้นหลังสีน้ำเงินเข้ม แสดงชื่อระบบ (Course Registration System) 
 *  - ฝั่งขวา (jPanel2)  : พื้นหลังสีเทาอ่อน มีฟอร์มกรอก Student ID / Password และปุ่ม Login
 */

public class LoginGUI extends JPanel {


    // ===================== ค่าคงที่กำหนดสไตล์และข้อความเริ่มต้น =====================

    // ข้อความ Placeholder สำหรับแสดงตัวอย่างในช่องกรอกข้อมูลเมื่อยังไม่ได้พิมพ์
    private static final String PLACEHOLDER_STUDENT_ID = "b6821234567";
    private static final String PLACEHOLDER_PASSWORD = "Input your password";

    // กำหนดโทนสีที่ใช้ในหน้าจอ
    private static final Color COLOR_PLACEHOLDER = new Color(204, 204, 204);   // สีเทาสำหรับ Placeholder
    private static final Color COLOR_TEXT_NORMAL = Color.BLACK;                // สีตัวอักษรเมื่อผู้ใช้พิมพ์จริง
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
    private JPasswordField passtext; // ช่องกรอก Password (ใช้ JPasswordField แทน JTextField)
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
     * สร้างแผงฝั่งซ้าย (jPanel1): แสดงโลโก้ ชื่อระบบ และวงกลมตกแต่ง
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
        jLabel9 = new JLabel("USER ID");
        jLabel9.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 12));
        jLabel9.setBounds(120, 220, 140, 20);
        jPanel2.add(jLabel9);

        usertext = new JTextField();
        usertext.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        usertext.setForeground(COLOR_PLACEHOLDER);
        usertext.setText(PLACEHOLDER_STUDENT_ID);
        usertext.setBounds(120, 240, 230, 40);
        attachStudentIdPlaceholder();
        usertext.addActionListener(this::usertextActionPerformed);
        jPanel2.add(usertext);

        // Header & Field: Password
        jLabel7 = new JLabel("Password");
        jLabel7.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 12));
        jLabel7.setBounds(120, 300, 140, 20);
        jPanel2.add(jLabel7);

        passtext = new JPasswordField();
        passtext.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        passtext.setForeground(COLOR_PLACEHOLDER);
        passtext.setText(PLACEHOLDER_PASSWORD);
        passtext.setEchoChar((char) 0); // แสดงข้อความ Placeholder ปกติก่อนที่ผู้ใช้จะเริ่มพิมพ์
        passtext.setBounds(120, 320, 230, 40);
        attachPasswordPlaceholder();
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

    // ===================== พฤติกรรม Placeholder ของช่องกรอกข้อมูล =====================

    /**
     * กำหนดพฤติกรรม Placeholder ให้ช่อง Student ID:
     *  - เมื่อคลิกเข้าช่อง (focusGained): ถ้าเป็นข้อความตัวอย่าง ให้ลบทิ้งและเปลี่ยนสีตัวอักษรเป็นสีดำ
     *  - เมื่อคลิกออกนอกช่อง (focusLost): ถ้าไม่ได้พิมพ์อะไรไว้ ให้ใส่ข้อความตัวอย่างกลับมาพร้อมสีเทา
     */
    private void attachStudentIdPlaceholder() {
        usertext.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent evt) {
                if (usertext.getText().equals(PLACEHOLDER_STUDENT_ID)) {
                    usertext.setText("");
                    usertext.setForeground(COLOR_TEXT_NORMAL);
                }
            }

            @Override
            public void focusLost(FocusEvent evt) {
                if (usertext.getText().trim().isEmpty()) {
                    usertext.setText(PLACEHOLDER_STUDENT_ID);
                    usertext.setForeground(COLOR_PLACEHOLDER);
                }
            }
        });
    }

    /**
     * กำหนดพฤติกรรม Placeholder ให้ช่อง Password:
     *  - เมื่อคลิกเข้าช่อง (focusGained): ลบคำว่า "Input your password" และตั้งค่าซ่อนรหัสผ่านเป็นจุด (•)
     *  - เมื่อคลิกออกนอกช่อง (focusLost): หากช่องว่าง ให้แสดงคำอธิบายและยกเลิกการซ่อนตัวอักษร
     */
    private void attachPasswordPlaceholder() {
        char defaultEchoChar = '•';
        passtext.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent evt) {
                if (String.valueOf(passtext.getPassword()).equals(PLACEHOLDER_PASSWORD)) {
                    passtext.setText("");
                    passtext.setEchoChar(defaultEchoChar); // ซ่อนรหัสผ่านเมื่อเริ่มพิมพ์จริง
                    passtext.setForeground(COLOR_TEXT_NORMAL);
                }
            }

            @Override
            public void focusLost(FocusEvent evt) {
                if (String.valueOf(passtext.getPassword()).trim().isEmpty()) {
                    passtext.setText(PLACEHOLDER_PASSWORD);
                    passtext.setEchoChar((char) 0); // แสดงข้อความปกติเมื่อแสดง Placeholder
                    passtext.setForeground(COLOR_PLACEHOLDER);
                }
            }
        });
    }

    // ===================== Event Handlers & Helper Methods =====================

    private void usertextActionPerformed(ActionEvent evt) {
        // TODO: สามารถใส่ Logic ที่ต้องการให้ทำงานเมื่อกด Enter ในช่อง Student ID
    }

    //เมื่อกดปุ่มล็อกอิน
   private void loginbuttonActionPerformed(ActionEvent evt) {

    String studentId = usertext.getText();
    String password = new String(passtext.getPassword());

    AuthenticationService authService = new AuthenticationService();

    LoginController controller = new LoginController(authService);

    controller.processLogin(studentId, password);

    this.setVisible(false);
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
