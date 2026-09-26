package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class StudentDashboard extends JPanel {

    // ===================== ตัวแปร Component =====================
    private JPanel sidePanel;
    private JPanel mainContentPanel;
    private JPanel tableContainerPanel;

    private JButton btnDashboard;
    private JButton btnRegisterCourse;
    private JButton btnDropCourse;
    private JButton btnLogout;

    private JLabel lblAppTitle1;
    private JLabel lblAppTitle2;
    private JLabel lblTitle;
    private JLabel lblWelcome;
    private JLabel lblTableTitle;

    private JTable tblCourses;
    private JScrollPane scrollPane;

    /**
     * คอนสตรัคเตอร์สร้างหน้า StudentDashboard
     */
    public StudentDashboard() {
        initComponents();
    }

    /**
     * สร้างและจัดวางตำแหน่ง UI ทั้งหมด
     */
    private void initComponents() {

        // กำหนดขนาดหน้าจอหลักรวม (970 x 600)
        setLayout(null);
        setPreferredSize(new Dimension(970, 600));

        // ------------------ 1. Side Panel (เมนูด้านซ้าย) ------------------
        sidePanel = new JPanel();
        sidePanel.setBackground(new Color(37, 51, 91));
        sidePanel.setLayout(null);
        sidePanel.setBounds(0, 0, 210, 600);

        // หัวข้อระบบ
        lblAppTitle1 = new JLabel("Course");
        lblAppTitle1.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblAppTitle1.setForeground(Color.WHITE);
        lblAppTitle1.setBounds(20, 30, 120, 30);
        sidePanel.add(lblAppTitle1);

        lblAppTitle2 = new JLabel("Registration");
        lblAppTitle2.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblAppTitle2.setForeground(Color.WHITE);
        lblAppTitle2.setBounds(20, 60, 160, 30);
        sidePanel.add(lblAppTitle2);

        // ปุ่มเมนูต่าง ๆ
        btnDashboard = createMenuButton("Dashboard", 130);
        btnDashboard.addActionListener(this::btnDashboardActionPerformed);
        sidePanel.add(btnDashboard);

        btnRegisterCourse = createMenuButton("Register Course", 190);
        btnRegisterCourse.addActionListener(this::btnRegisterCourseActionPerformed);
        sidePanel.add(btnRegisterCourse);

        btnDropCourse = createMenuButton("Drop course", 250);
        btnDropCourse.addActionListener(this::btnDropCourseActionPerformed);
        sidePanel.add(btnDropCourse);

        btnLogout = createMenuButton("Logout", 310);
        btnLogout.addActionListener(this::btnLogoutActionPerformed);
        sidePanel.add(btnLogout);

        add(sidePanel);

        // ------------------ 2. Main Content Panel (พื้นที่แสดงผลฝั่งขวา) ------------------
        mainContentPanel = new JPanel();
        mainContentPanel.setBackground(new Color(236, 238, 248));
        mainContentPanel.setLayout(null);
        mainContentPanel.setBounds(210, 0, 760, 600);

        // ข้อความต้อนรับ
        lblTitle = new JLabel("Student Dashboard");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lblTitle.setForeground(new Color(37, 51, 91));
        lblTitle.setBounds(30, 30, 440, 40);
        mainContentPanel.add(lblTitle);

        lblWelcome = new JLabel("Welcome, Student");
        lblWelcome.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        lblWelcome.setForeground(new Color(86, 101, 144));
        lblWelcome.setBounds(30, 70, 230, 30);
        mainContentPanel.add(lblWelcome);

        // กรอบตาราง My Registered Courses
        tableContainerPanel = new JPanel();
        tableContainerPanel.setBackground(Color.WHITE);
        tableContainerPanel.setLayout(null);
        tableContainerPanel.setBounds(20, 110, 710, 450);

        lblTableTitle = new JLabel("My Registered Courses");
        lblTableTitle.setFont(new Font("Leelawadee UI", Font.PLAIN, 20));
        lblTableTitle.setForeground(new Color(37, 51, 91));
        lblTableTitle.setBounds(20, 10, 300, 30);
        tableContainerPanel.add(lblTableTitle);

        // สร้าง JTable (5 คอลัมน์)
        String[] columns = {"Course ID", "Course Name", "Credit", "Section", "Teacher"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        tblCourses = new JTable(model);
        tblCourses.setRowHeight(61); // กำหนดความสูงแถวตามต้นฉบับ

        scrollPane = new JScrollPane(tblCourses);
        scrollPane.setBounds(20, 50, 670, 380);
        tableContainerPanel.add(scrollPane);

        mainContentPanel.add(tableContainerPanel);

        add(mainContentPanel);
    }

    /**
     * ฟังก์ชันสำหรับสร้างปุ่มเมนูด้านซ้ายแบบรียูส
     */
    private JButton createMenuButton(String text, int yPosition) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setBackground(new Color(83, 96, 134));
        btn.setForeground(Color.WHITE);
        btn.setBounds(20, yPosition, 170, 40);
        btn.setFocusPainted(false);
        return btn;
    }

    /**
     * ฟังก์ชันสลับหน้าภายใน mainContentPanel
     */
    private void showSubPanel(JPanel panel) {
        mainContentPanel.removeAll();
        mainContentPanel.setLayout(new BorderLayout());
        mainContentPanel.add(panel, BorderLayout.CENTER);
        mainContentPanel.revalidate();
        mainContentPanel.repaint();
    }

    // ===================== Event Handlers =====================

    private void btnDashboardActionPerformed(ActionEvent evt) {
        showSubPanel(new Dashboard());
    }

    private void btnRegisterCourseActionPerformed(ActionEvent evt) {
        showSubPanel(new RegisterCourse());
    }

    private void btnDropCourseActionPerformed(ActionEvent evt) {
        showSubPanel(new DropCourse());
    }

    private void btnLogoutActionPerformed(ActionEvent evt) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (frame != null) {
            frame.setContentPane(new LoginGUI());
            frame.revalidate();
            frame.repaint();
        }
    }
}
