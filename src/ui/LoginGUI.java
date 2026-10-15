package ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import controller.LoginController;
import model.Student;
import controller.AdminController;
import model.Admin;

/**
 * หน้า Login ของระบบลงทะเบียนเรียน
 * ซ้าย = แบนเนอร์ชื่อระบบ, ขวา = ฟอร์มกรอก ID/Password
 * ใช้ฟอร์มเดียวกันสำหรับทั้งนักศึกษาและ admin (ระบบตรวจ admin ก่อน แล้วค่อยตรวจนักศึกษา)
 */
public class LoginGUI extends JPanel {

    private final LoginController loginController = new LoginController();
    private final AdminController adminController = new AdminController();

    // สีของธีม
    private static final Color COLOR_BRAND_BG = new Color(37, 51, 91);
    private static final Color COLOR_PAGE_BG = new Color(236, 238, 248);
    private static final Color COLOR_BUTTON_BG = new Color(83, 96, 134);
    private static final Color COLOR_LINK = new Color(0, 0, 204);

    private JPanel leftPanel;   // ฝั่งซ้าย (แบนเนอร์)
    private JPanel rightPanel;  // ฝั่งขวา (ฟอร์ม)
    private JLabel lblSignInTitle;
    private JLabel lblStudentId;
    private JLabel lblPassword;
    private JLabel lblNoAccount;
    private JLabel lblSignUp;

    private JTextField txtStudentId;    // ช่องกรอกรหัส
    private JPasswordField txtPassword; // ช่องกรอกรหัสผ่าน (แสดงเป็นจุด)
    private JButton btnLogin;

    public LoginGUI() {
        initComponents();
    }

    private void initComponents() {
        setLayout(null);
        setPreferredSize(new Dimension(970, 600));

        buildLeftBrandingPanel();
        buildRightLoginPanel();
    }

    /** สร้างฝั่งซ้าย: พื้นสีกรมท่าพร้อมข้อความชื่อระบบ */
    private void buildLeftBrandingPanel() {
        leftPanel = new JPanel();
        leftPanel.setBackground(COLOR_BRAND_BG);
        leftPanel.setLayout(null);
        leftPanel.setBounds(0, 0, 510, 600);

        leftPanel.add(createBrandLabel("Course", 80, 210, 250, 40));
        leftPanel.add(createBrandLabel("Registration", 80, 240, 300, 60));
        leftPanel.add(createBrandLabel("System", 80, 290, 250, 60));

        add(leftPanel);
    }

    /** สร้างป้ายข้อความสีขาวตัวใหญ่สำหรับแบนเนอร์ */
    private JLabel createBrandLabel(String text, int x, int y, int width, int height) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 36));
        label.setForeground(Color.WHITE);
        label.setBounds(x, y, width, height);
        return label;
    }

    /** สร้างฝั่งขวา: ฟอร์ม Login และลิงก์ Sign up */
    private void buildRightLoginPanel() {
        rightPanel = new JPanel();
        rightPanel.setBackground(COLOR_PAGE_BG);
        rightPanel.setLayout(null);
        rightPanel.setBounds(510, 0, 460, 600);

        lblSignInTitle = new JLabel("Sign in to continue");
        lblSignInTitle.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 22));
        lblSignInTitle.setBounds(120, 170, 250, 30);
        rightPanel.add(lblSignInTitle);

        // ช่อง Student ID (กด Enter เพื่อ login ได้)
        lblStudentId = new JLabel("Student ID");
        lblStudentId.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 12));
        lblStudentId.setBounds(120, 220, 140, 20);
        rightPanel.add(lblStudentId);

        txtStudentId = new JTextField();
        txtStudentId.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        txtStudentId.setBounds(120, 240, 230, 40);
        txtStudentId.addActionListener(this::btnLoginActionPerformed);
        rightPanel.add(txtStudentId);

        // ช่อง Password (กด Enter เพื่อ login ได้)
        lblPassword = new JLabel("Password");
        lblPassword.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 12));
        lblPassword.setBounds(120, 300, 140, 20);
        rightPanel.add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        txtPassword.setBounds(120, 320, 230, 40);
        txtPassword.addActionListener(this::btnLoginActionPerformed);
        rightPanel.add(txtPassword);

        // ปุ่ม Login
        btnLogin = new JButton("Login");
        btnLogin.setBackground(COLOR_BUTTON_BG);
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setBounds(160, 390, 140, 30);
        btnLogin.addActionListener(this::btnLoginActionPerformed);
        rightPanel.add(btnLogin);

        // ข้อความ + ลิงก์ไปหน้าสมัครสมาชิก
        lblNoAccount = new JLabel("Don't have an account?");
        lblNoAccount.setBounds(130, 430, 150, 20);
        rightPanel.add(lblNoAccount);

        lblSignUp = new JLabel("Sign up");
        lblSignUp.setForeground(COLOR_LINK);
        lblSignUp.setCursor(new Cursor(Cursor.HAND_CURSOR)); // เมาส์ชี้แล้วเป็นรูปมือ
        lblSignUp.setBounds(275, 430, 60, 20);
        lblSignUp.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                lblSignUpMouseClicked(evt);
            }
        });
        rightPanel.add(lblSignUp);

        add(rightPanel);
    }

    /**
     * จัดการการ login (ทั้งกดปุ่มและกด Enter)
     * ลำดับ: ลองเป็น admin ก่อน -> ถ้าไม่ใช่ ลองเป็นนักศึกษา
     */
    private void btnLoginActionPerformed(ActionEvent evt) {
        String studentId = txtStudentId.getText();
        String password = new String(txtPassword.getPassword());

        // 1) ลอง login เป็น admin
        Admin admin = adminController.login(studentId, password);
    if (admin != null) {
        // เป็น admin -> สลับไปหน้า AdminDashboard
        JFrame f = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (f != null) {
            f.setTitle("Course Registration - Admin");
            f.setContentPane(new AdminDashboard(admin));
            f.revalidate();
            f.repaint();
        }
        return;
    }

        // 2) ไม่ใช่ admin -> ลอง login เป็นนักศึกษา
        try {
            Student student = loginController.login(studentId, password);

            // สำเร็จ -> สลับไปหน้า StudentDashboard
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            if (frame != null) {
                frame.setTitle("Course Registration - Student Dashboard");
                frame.setContentPane(new StudentDashboard(student));
                frame.revalidate();
                frame.repaint();
            }
        } catch (IllegalArgumentException e) {
            // กรอกไม่ครบ หรือ ID/Password ผิด
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /** คลิกลิงก์ "Sign up": ไปหน้าสมัครสมาชิก */
    private void lblSignUpMouseClicked(MouseEvent evt) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (frame != null) {
            frame.setContentPane(new AccountRegister());
            frame.revalidate();
            frame.repaint();
        }
    }

    /** main สำหรับเปิดหน้านี้ทดสอบเดี่ยว ๆ (โปรแกรมจริงเริ่มที่ main.Main) */
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
