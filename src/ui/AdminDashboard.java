package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AdminDashboard extends JPanel {

    // ===================== ตัวแปร Component ของ Swing =====================

    // แผงเมนูหลักฝั่งซ้ายและฝั่งขวา
    private JPanel sidebarPanel;
    private JPanel mainContentPanel;

    // การ์ดแสดงผลข้อมูล
    private JPanel totalCoursesCard;
    private JPanel subCardPanel;

    // หัวข้อและข้อความแสดงผล (Sidebar & Top Bar)
    private JLabel lblHeaderCourse;
    private JLabel lblSidebarCourse;
    private JLabel lblSidebarRegistration;
    private JLabel lblTitle;
    private JLabel lblWelcome;

    // ข้อความแสดงผลในการ์ด (Total Courses Card)
    private JLabel lblTotalCoursesTitle;
    private JLabel lblTotalCoursesCount;
    private JLabel lblSubCardTitle;
    private JLabel lblSubCardCount;

    // ปุ่มกดเมนูฝั่งซ้าย
    private JButton btnLogout;
    private JButton btnDashboard;
    private JButton btnManageCourses;
    private JButton btnNav3;
    private JButton btnNav4;

    /**
     * คอนสตรัคเตอร์สร้างหน้า Admin Dashboard
     */
    public AdminDashboard() {
        initComponents();
    }

    /**
     * สร้างและจัดวางตำแหน่ง UI ทั้งหมด
     */
    private void initComponents() {

        // กำหนด ขนาดหน้าจอรวม ( Width: 970, Height: 600 )
        setLayout(null);
        setPreferredSize(new Dimension(970, 600));

        // ------------------ 1. Header Unused Label ------------------
        lblHeaderCourse = new JLabel();
        lblHeaderCourse.setBackground(new Color(255, 255, 255));
        lblHeaderCourse.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lblHeaderCourse.setForeground(new Color(255, 255, 255));
        lblHeaderCourse.setText("Course");

        // ------------------ 2. Sidebar Panel (ฝั่งซ้าย) ------------------
        sidebarPanel = new JPanel();
        sidebarPanel.setBackground(new Color(37, 51, 91));
        sidebarPanel.setLayout(null);
        sidebarPanel.setBounds(0, 0, 210, 600);

        lblSidebarCourse = new JLabel();
        lblSidebarCourse.setBackground(new Color(255, 255, 255));
        lblSidebarCourse.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblSidebarCourse.setForeground(new Color(255, 255, 255));
        lblSidebarCourse.setText("Course");
        lblSidebarCourse.setBounds(20, 30, 120, 30);
        sidebarPanel.add(lblSidebarCourse);

        lblSidebarRegistration = new JLabel();
        lblSidebarRegistration.setBackground(new Color(255, 255, 255));
        lblSidebarRegistration.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblSidebarRegistration.setForeground(new Color(255, 255, 255));
        lblSidebarRegistration.setText("Registration");
        lblSidebarRegistration.setBounds(20, 60, 160, 30);
        sidebarPanel.add(lblSidebarRegistration);

        // ปุ่มเมนู Dashboard (ปุ่มที่ 1)
        btnDashboard = new JButton();
        btnDashboard.setBackground(new Color(83, 96, 134));
        btnDashboard.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnDashboard.setForeground(new Color(255, 255, 255));
        btnDashboard.setText("Dashboard");
        btnDashboard.setBounds(20, 130, 170, 40);
        btnDashboard.addActionListener(this::btnDashboardActionPerformed);
        sidebarPanel.add(btnDashboard);

        // ปุ่มเมนู Manage Courses
        btnManageCourses = new JButton();
        btnManageCourses.setBackground(new Color(83, 96, 134));
        btnManageCourses.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnManageCourses.setForeground(new Color(255, 255, 255));
        btnManageCourses.setText("Manage Courses");
        btnManageCourses.setBounds(20, 190, 170, 40);
        btnManageCourses.addActionListener(this::btnManageCoursesActionPerformed);
        sidebarPanel.add(btnManageCourses);

        // ปุ่มเมนู Nav 3
        btnNav3 = new JButton();
        btnNav3.setBackground(new Color(83, 96, 134));
        btnNav3.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnNav3.setForeground(new Color(255, 255, 255));
        btnNav3.setText("Dashboard");
        btnNav3.setBounds(20, 250, 170, 40);
        btnNav3.addActionListener(this::btnNav3ActionPerformed);
        sidebarPanel.add(btnNav3);

        // ปุ่มเมนู Nav 4
        btnNav4 = new JButton();
        btnNav4.setBackground(new Color(83, 96, 134));
        btnNav4.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnNav4.setForeground(new Color(255, 255, 255));
        btnNav4.setText("Dashboard");
        btnNav4.setBounds(20, 310, 170, 40);
        btnNav4.addActionListener(this::btnNav4ActionPerformed);
        sidebarPanel.add(btnNav4);

        // ปุ่ม Logout
        btnLogout = new JButton();
        btnLogout.setBackground(new Color(83, 96, 134));
        btnLogout.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnLogout.setForeground(new Color(255, 255, 255));
        btnLogout.setText("Logout");
        btnLogout.setBounds(20, 370, 170, 40);
        btnLogout.addActionListener(this::btnLogoutActionPerformed);
        sidebarPanel.add(btnLogout);

        add(sidebarPanel);

        // ------------------ 3. Main Content Panel (ฝั่งขวา) ------------------
        mainContentPanel = new JPanel();
        mainContentPanel.setBackground(new Color(236, 238, 248));
        mainContentPanel.setLayout(null);
        mainContentPanel.setBounds(210, 0, 760, 600);

        lblTitle = new JLabel();
        lblTitle.setBackground(new Color(255, 255, 255));
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lblTitle.setForeground(new Color(37, 51, 91));
        lblTitle.setText("Admin Dashboard");
        lblTitle.setBounds(30, 30, 440, 30);
        mainContentPanel.add(lblTitle);

        lblWelcome = new JLabel();
        lblWelcome.setBackground(new Color(255, 255, 255));
        lblWelcome.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        lblWelcome.setForeground(new Color(86, 101, 144));
        lblWelcome.setText("Welcome, Admin ");
        lblWelcome.setBounds(30, 70, 230, 30);
        mainContentPanel.add(lblWelcome);

        // ------------------ 4. Total Courses Card ------------------
        totalCoursesCard = new JPanel();
        totalCoursesCard.setBackground(new Color(255, 255, 255));
        totalCoursesCard.setLayout(null);
        totalCoursesCard.setBounds(30, 130, 180, 100);

        lblTotalCoursesTitle = new JLabel();
        lblTotalCoursesTitle.setBackground(new Color(255, 255, 255));
        lblTotalCoursesTitle.setForeground(new Color(86, 101, 144));
        lblTotalCoursesTitle.setText("Total Courses");
        lblTotalCoursesTitle.setBounds(20, 10, 160, 30);
        totalCoursesCard.add(lblTotalCoursesTitle);

        lblTotalCoursesCount = new JLabel();
        lblTotalCoursesCount.setBackground(new Color(255, 255, 255));
        lblTotalCoursesCount.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lblTotalCoursesCount.setForeground(new Color(37, 51, 91));
        lblTotalCoursesCount.setText("0");
        lblTotalCoursesCount.setBounds(20, 40, 160, 30);
        totalCoursesCard.add(lblTotalCoursesCount);

        // Sub Card ภายใน Total Courses Card
        subCardPanel = new JPanel();
        subCardPanel.setBackground(new Color(255, 255, 255));
        subCardPanel.setLayout(null);
        subCardPanel.setBounds(30, 130, 180, 100);

        lblSubCardTitle = new JLabel();
        lblSubCardTitle.setBackground(new Color(255, 255, 255));
        lblSubCardTitle.setForeground(new Color(86, 101, 144));
        lblSubCardTitle.setText("Total Courses");
        lblSubCardTitle.setBounds(20, 10, 160, 30);
        subCardPanel.add(lblSubCardTitle);

        lblSubCardCount = new JLabel();
        lblSubCardCount.setBackground(new Color(255, 255, 255));
        lblSubCardCount.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lblSubCardCount.setForeground(new Color(37, 51, 91));
        lblSubCardCount.setText("0");
        lblSubCardCount.setBounds(20, 40, 160, 30);
        subCardPanel.add(lblSubCardCount);

        totalCoursesCard.add(subCardPanel);
        mainContentPanel.add(totalCoursesCard);

        add(mainContentPanel);
    }

    // ===================== Action Listeners / Event Handlers =====================

    private void btnLogoutActionPerformed(ActionEvent evt) {
        // TODO: ใส่ Logic เมื่อกดปุ่ม Logout
    }

    private void btnDashboardActionPerformed(ActionEvent evt) {
        // TODO: ใส่ Logic เมื่อกดปุ่ม Dashboard
    }

    private void btnManageCoursesActionPerformed(ActionEvent evt) {
        // TODO: ใส่ Logic เมื่อกดปุ่ม Manage Courses
    }

    private void btnNav3ActionPerformed(ActionEvent evt) {
        // TODO: ใส่ Logic เมื่อกดปุ่ม Nav 3
    }

    private void btnNav4ActionPerformed(ActionEvent evt) {
        // TODO: ใส่ Logic เมื่อกดปุ่ม Nav 4
    }
}