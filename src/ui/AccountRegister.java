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

/**
 * หน้าสมัครสมาชิก (Account Register) ของระบบลงทะเบียนเรียน
 */
public class AccountRegister extends JPanel {

    // ===================== ค่าคงที่กำหนดสไตล์ =====================
    private static final Color COLOR_BRAND_BG = new Color(37, 51, 91);     // สีน้ำเงินเข้มฝั่งซ้าย
    private static final Color COLOR_PAGE_BG = new Color(236, 238, 248);   // สีเทาอ่อนฝั่งขวา
    private static final Color COLOR_BUTTON_BG = new Color(76, 110, 245);  // สีฟ้าปุ่ม สมัครสมาชิก
    private static final Color COLOR_LINK = new Color(0, 0, 204);          // สีน้ำเงินลิงก์ Sign in

    // ===================== ตัวแปร Component =====================
    // Components ฝั่งซ้าย (Branding Area)
    private JPanel leftPanel;
    private JLabel lblCourse;
    private JLabel lblRegistration;
    private JLabel lblSystem;

    // Components ฝั่งขวา (Register Form Area)
    private JPanel rightPanel;
    private JLabel lblTitle;
    private JLabel lblStudentId;
    private JLabel lblName;
    private JLabel lblEmail;
    private JLabel lblPassword;
    private JLabel lblConfirmPassword;
    private JLabel lblAlreadyHaveAccount;
    private JLabel lblSignIn;

    private JTextField txtStudentId;
    private JTextField txtName;
    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JPasswordField txtConfirmPassword;
    private JButton btnRegister;

    /**
     * คอนสตรัคเตอร์สร้างหน้าสมัครสมาชิก
     */
    public AccountRegister() {
        initComponents();
    }

    // ===================== สร้างและจัดวาง UI ทั้งหมด =====================

    private void initComponents() {
        setLayout(null);
        setPreferredSize(new Dimension(970, 600));

        buildLeftBrandingPanel();
        buildRightRegisterPanel();
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
     * แผงฝั่งขวา: ฟอร์มสมัครสมาชิก
     */
    private void buildRightRegisterPanel() {
        rightPanel = new JPanel();
        rightPanel.setBackground(COLOR_PAGE_BG);
        rightPanel.setLayout(null);
        rightPanel.setBounds(510, 0, 460, 600);

        // หัวข้อ "Create new account"
        lblTitle = new JLabel("Create new account");
        lblTitle.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 22));
        lblTitle.setBounds(120, 40, 250, 30);
        rightPanel.add(lblTitle);

        // Student ID
        lblStudentId = new JLabel("Student ID");
        lblStudentId.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 12));
        lblStudentId.setBounds(120, 85, 140, 20);
        rightPanel.add(lblStudentId);

        txtStudentId = new JTextField();
        txtStudentId.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtStudentId.setBounds(120, 105, 230, 35);
        rightPanel.add(txtStudentId);

        // Name
        lblName = new JLabel("Name");
        lblName.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 12));
        lblName.setBounds(120, 150, 140, 20);
        rightPanel.add(lblName);

        txtName = new JTextField();
        txtName.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtName.setBounds(120, 170, 230, 35);
        rightPanel.add(txtName);

        // Email
        lblEmail = new JLabel("Email");
        lblEmail.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 12));
        lblEmail.setBounds(120, 215, 140, 20);
        rightPanel.add(lblEmail);

        txtEmail = new JTextField();
        txtEmail.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtEmail.setBounds(120, 235, 230, 35);
        rightPanel.add(txtEmail);

        // Password
        lblPassword = new JLabel("Password");
        lblPassword.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 12));
        lblPassword.setBounds(120, 280, 140, 20);
        rightPanel.add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtPassword.setBounds(120, 300, 230, 35);
        rightPanel.add(txtPassword);

        // Confirm Password
        lblConfirmPassword = new JLabel("Confirm Password");
        lblConfirmPassword.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 12));
        lblConfirmPassword.setBounds(120, 345, 140, 20);
        rightPanel.add(lblConfirmPassword);

        txtConfirmPassword = new JPasswordField();
        txtConfirmPassword.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtConfirmPassword.setBounds(120, 365, 230, 35);
        rightPanel.add(txtConfirmPassword);

        // ปุ่ม Register (เปลี่ยนข้อความจาก Login เป็น Register ให้ถูกต้อง)
        btnRegister = new JButton("Register");
        btnRegister.setBackground(COLOR_BUTTON_BG);
        btnRegister.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnRegister.setForeground(Color.WHITE);
        btnRegister.setFocusPainted(false);
        btnRegister.setBounds(160, 425, 140, 35);
        btnRegister.addActionListener(this::btnRegisterActionPerformed);
        rightPanel.add(btnRegister);

        // ลิงก์ไปยังหน้า Login (Sign in)
        lblAlreadyHaveAccount = new JLabel("Already have an account?");
        lblAlreadyHaveAccount.setBounds(120, 480, 160, 20);
        rightPanel.add(lblAlreadyHaveAccount);

        lblSignIn = new JLabel("Sign in");
        lblSignIn.setForeground(COLOR_LINK);
        lblSignIn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblSignIn.setBounds(275, 480, 60, 20);
        lblSignIn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                lblSignInMouseClicked(evt);
            }
        });
        rightPanel.add(lblSignIn);

        add(rightPanel);
    }

    // ===================== Event Handlers =====================

    private void btnRegisterActionPerformed(ActionEvent evt) {
        String studentId = txtStudentId.getText().trim();
        String name = txtName.getText().trim();
        String email = txtEmail.getText().trim();
        String password = new String(txtPassword.getPassword());
        String confirmPassword = new String(txtConfirmPassword.getPassword());

        if (studentId.isEmpty() || name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this, "Account created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        navigateToLogin();
    }

    private void lblSignInMouseClicked(MouseEvent evt) {
        navigateToLogin();
    }

    private void navigateToLogin() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (frame != null) {
            frame.setTitle("Course Registration - Login");
            frame.setContentPane(new LoginGUI());
            frame.revalidate();
            frame.repaint();
        }
    }

    // เมธอด main สำหรับทดสอบรันเฉพาะหน้า Register
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Course Registration - Register");
            frame.setContentPane(new AccountRegister());
            frame.pack();
            frame.setResizable(false);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}

