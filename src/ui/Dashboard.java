package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Dashboard extends JPanel {

    // ===================== ตัวแปร Component =====================
    private JPanel totalCoursesCard;

    private JLabel lblTitle;
    private JLabel lblWelcome;
    private JLabel lblTotalCoursesTitle;
    private JLabel lblTotalCoursesCount;

    /**
     * คอนสตรัคเตอร์สร้างหน้า Dashboard
     */
    public Dashboard() {
        initComponents();
    }

    /**
     * สร้างและจัดวางตำแหน่ง UI ทั้งหมด
     */
    private void initComponents() {

        // กำหนดขนาด panel รวม (760 x 600) ให้พอดีกับช่องฝั่งขวา
        setLayout(null);
        setPreferredSize(new Dimension(760, 600));
        setBackground(new Color(236, 238, 248));

        // ------------------ 1. Header Title ------------------
        lblTitle = new JLabel("Admin Dashboard");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lblTitle.setForeground(new Color(37, 51, 91));
        lblTitle.setBounds(30, 30, 440, 30);
        add(lblTitle);

        lblWelcome = new JLabel("Welcome, Admin ");
        lblWelcome.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        lblWelcome.setForeground(new Color(86, 101, 144));
        lblWelcome.setBounds(30, 70, 230, 30);
        add(lblWelcome);

        // ------------------ 2. Total Courses Card ------------------
        totalCoursesCard = new JPanel();
        totalCoursesCard.setBackground(new Color(255, 255, 255));
        totalCoursesCard.setLayout(null);
        totalCoursesCard.setBounds(30, 130, 180, 100);

        // หัวข้อของการ์ด
        lblTotalCoursesTitle = new JLabel("Total Courses");
        lblTotalCoursesTitle.setForeground(new Color(86, 101, 144));
        lblTotalCoursesTitle.setBounds(20, 10, 160, 30);
        totalCoursesCard.add(lblTotalCoursesTitle);

        // ตัวเลขแสดงผลในตัวการ์ด
        lblTotalCoursesCount = new JLabel("0");
        lblTotalCoursesCount.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lblTotalCoursesCount.setForeground(new Color(37, 51, 91));
        lblTotalCoursesCount.setBounds(20, 40, 160, 30);
        totalCoursesCard.add(lblTotalCoursesCount);

        add(totalCoursesCard);
    }
}