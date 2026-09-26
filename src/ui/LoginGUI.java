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

/**
 * หน้า Login ของระบบลงทะเบียนเรียน (รองรับการทำงานบน VS Code)
 */
public class LoginGUI extends JPanel {

    private final LoginController loginController = new LoginController();

    // ===================== ค่าคงที่กำหนดสไตล์ =====================
    private static final Color COLOR_BRAND_BG = new Color(37, 51, 91);     // สีน้ำเงินเข้มฝั่งซ้าย
    private static final Color COLOR_PAGE_BG = new Color(236, 238, 248);   // สีเทาอ่อนฝั่งขวา
    private static final Color COLOR_BUTTON_BG = new Color(76, 110, 245);  // สีฟ้าปุ่ม Login
    private static final Color COLOR_LINK = new Color(0, 0, 204);          // สีน้ำเงินลิงก์ Sign up

    // ===================== ตัวแปร Component =====================
    // Components ฝั่งซ้าย (Branding Area)
    private JPanel leftPanel;
    private JLabel lblCourse;
    private JLabel lblRegistration;
    private JLabel lblSystem;

    // Components ฝั่งขวา (Login Form Area)
    private JPanel rightPanel;
    private JLabel lblSignInTitle;
    private JLabel lblStudentId;
    private JLabel lblPassword;
    private JLabel lblNoAccount;
    private JLabel lblSignUp;
    
    private JTextField txtStudentId;
    private JPasswordField txtPassword;
    private JButton btnLogin;

    /**
     * คอนสตรัคเตอร์สร้างหน้าจอ Login
     */
    public LoginGUI() {
        initComponents();
    }

    // ===================== สร้างและจัดวาง UI ทั้งหมด =====================

    private void initComponents() {
        setLayout(null);
        setPreferredSize(new Dimension(970, 600));

        buildLeftBrandingPanel();
        buildRightLoginPanel();
    }

    /**
     * แผงฝั่งซ้าย: แสดงชื่อระบบ
     */
    private void buildLeftBrandingPanel() {
        leftPanel = new JPanel();
        leftPanel.setBackground(COLOR_BRAND_BG);
        leftPanel.setLayout(null);
        leftPanel.setBounds(0, 0, 510, 600);

        lblCourse = createBrandLabel("Course", 80, 210, 250, 40);
        lblRegistration = createBrandLabel("Registration", 80, 240, 300, 60);
        lblSystem = createBrandLabel("System", 80, 290, 250, 60);

        leftPanel.add(lblCourse);
        leftPanel.add(lblRegistration);
        leftPanel.add(lblSystem);

        add(leftPanel);
    }

    private JLabel createBrandLabel(String text, int x, int y, int width, int height) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 36));
        label.setForeground(Color.WHITE);
        label.setBounds(x, y, width, height);
        return label;
    }

    /**
     * แผงฝั่งขวา: ฟอร์มกรอกข้อมูลและปุ่มดำเนินการ
     */
    private void buildRightLoginPanel() {
        rightPanel = new JPanel();
        rightPanel.setBackground(COLOR_PAGE_BG);
        rightPanel.setLayout(null);
        rightPanel.setBounds(510, 0, 460, 600);

        // หัวข้อ "Sign in to continue"
        lblSignInTitle = new JLabel("Sign in to continue");
        lblSignInTitle.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 22));
        lblSignInTitle.setBounds(120, 170, 250, 30);
        rightPanel.add(lblSignInTitle);

        // Label & Field: Student ID
        lblStudentId = new JLabel("Student ID");
        lblStudentId.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 12));
        lblStudentId.setBounds(120, 220, 140, 20);
        rightPanel.add(lblStudentId);

        txtStudentId = new JTextField();
        txtStudentId.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        txtStudentId.setBounds(120, 240, 230, 40);
        txtStudentId.addActionListener(this::btnLoginActionPerformed);
        rightPanel.add(txtStudentId);

        // Label & Field: Password
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

        // ลิงก์ไปยังหน้าสมัครสมาชิก (Sign up)
        lblNoAccount = new JLabel("Don't have an account?");
        lblNoAccount.setBounds(130, 430, 150, 20);
        rightPanel.add(lblNoAccount);

        lblSignUp = new JLabel("Sign up");
        lblSignUp.setForeground(COLOR_LINK);
        lblSignUp.setCursor(new Cursor(Cursor.HAND_CURSOR));
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

    // ===================== Event Handlers =====================

    private void btnLoginActionPerformed(ActionEvent evt) {
        String studentId = txtStudentId.getText();
        String password = new String(txtPassword.getPassword());

        try {
            String role = loginController.login(studentId, password);

            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            if (frame != null) {
                if ("STUDENT".equalsIgnoreCase(role)) {
                    frame.setTitle("Course Registration - Student Dashboard");
                    frame.setContentPane(new StudentDashboard());
                }
                frame.revalidate();
                frame.repaint();
            }

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void lblSignUpMouseClicked(MouseEvent evt) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (frame != null) {
            frame.setContentPane(new AccountRegister()); // สลับไปหน้าสมัครสมาชิก
            frame.revalidate();
            frame.repaint();
        }
    }

    // เมธอด main สำหรับทดสอบรันเฉพาะหน้า Login
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
