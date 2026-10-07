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

import controller.RegisterController;

/**
 * หน้าสมัครสมาชิก (Account Register)
 */
public class AccountRegister extends JPanel {

    private final RegisterController registerController = new RegisterController();

    private static final Color COLOR_BRAND_BG = new Color(37, 51, 91);
    private static final Color COLOR_PAGE_BG = new Color(236, 238, 248);
    private static final Color COLOR_BUTTON_BG = new Color(83, 96, 134);
    private static final Color COLOR_LINK = new Color(0, 0, 204);

    private JPanel leftPanel;
    private JPanel rightPanel;

    private JTextField txtStudentId;
    private JTextField txtName;
    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JPasswordField txtConfirmPassword;
    private JButton btnRegister;

    public AccountRegister() {
        initComponents();
    }

    private void initComponents() {
        setLayout(null);
        setPreferredSize(new Dimension(970, 600));

        buildLeftBrandingPanel();
        buildRightRegisterPanel();
    }

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

    private JLabel createBrandLabel(String text, int x, int y, int width, int height) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 36));
        label.setForeground(Color.WHITE);
        label.setBounds(x, y, width, height);
        return label;
    }

    private void buildRightRegisterPanel() {
        rightPanel = new JPanel();
        rightPanel.setBackground(COLOR_PAGE_BG);
        rightPanel.setLayout(null);
        rightPanel.setBounds(510, 0, 460, 600);

        JLabel lblTitle = new JLabel("Create new account");
        lblTitle.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 22));
        lblTitle.setBounds(120, 40, 250, 30);
        rightPanel.add(lblTitle);

        rightPanel.add(fieldLabel("Student ID", 85));
        txtStudentId = new JTextField();
        txtStudentId.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtStudentId.setBounds(120, 105, 230, 35);
        rightPanel.add(txtStudentId);

        rightPanel.add(fieldLabel("Name", 150));
        txtName = new JTextField();
        txtName.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtName.setBounds(120, 170, 230, 35);
        rightPanel.add(txtName);

        rightPanel.add(fieldLabel("Email", 215));
        txtEmail = new JTextField();
        txtEmail.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtEmail.setBounds(120, 235, 230, 35);
        rightPanel.add(txtEmail);

        rightPanel.add(fieldLabel("Password", 280));
        txtPassword = new JPasswordField();
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtPassword.setBounds(120, 300, 230, 35);
        rightPanel.add(txtPassword);

        rightPanel.add(fieldLabel("Confirm Password", 345));
        txtConfirmPassword = new JPasswordField();
        txtConfirmPassword.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtConfirmPassword.setBounds(120, 365, 230, 35);
        rightPanel.add(txtConfirmPassword);

        btnRegister = new JButton("Register");
        btnRegister.setBackground(COLOR_BUTTON_BG);
        btnRegister.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnRegister.setForeground(Color.WHITE);
        btnRegister.setFocusPainted(false);
        btnRegister.setBounds(160, 425, 140, 35);
        btnRegister.addActionListener(this::btnRegisterActionPerformed);
        rightPanel.add(btnRegister);

        JLabel lblAlreadyHaveAccount = new JLabel("Already have an account?");
        lblAlreadyHaveAccount.setBounds(120, 480, 160, 20);
        rightPanel.add(lblAlreadyHaveAccount);

        JLabel lblSignIn = new JLabel("Sign in");
        lblSignIn.setForeground(COLOR_LINK);
        lblSignIn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblSignIn.setBounds(275, 480, 60, 20);
        lblSignIn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                navigateToLogin();
            }
        });
        rightPanel.add(lblSignIn);

        add(rightPanel);
    }

    private JLabel fieldLabel(String text, int y) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 12));
        label.setBounds(120, y, 140, 20);
        return label;
    }

    private void btnRegisterActionPerformed(ActionEvent evt) {
        String studentId = txtStudentId.getText().trim();
        String name = txtName.getText().trim();
        String email = txtEmail.getText().trim();
        String password = new String(txtPassword.getPassword());
        String confirmPassword = new String(txtConfirmPassword.getPassword());

        try {
            registerController.register(studentId, name, email, password, confirmPassword);
            JOptionPane.showMessageDialog(this, "Account created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            navigateToLogin();
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
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
