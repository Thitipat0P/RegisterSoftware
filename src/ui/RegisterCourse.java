package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * หน้า Sub-panel สำหรับแสดงรายวิชาที่เปิดให้ลงทะเบียน (Register Course)
 */
public class RegisterCourse extends JPanel {

    // ===================== ตัวแปร Component =====================
    private JLabel lblTitle;
    private JPanel tableContainerPanel;
    private JLabel lblTableTitle;
    private JTable tblCourses;
    private JScrollPane scrollPane;
    private JButton btnRegister;

    /**
     * คอนสตรัคเตอร์สร้างหน้า RegisterCourse
     */
    public RegisterCourse() {
        initComponents();
    }

    /**
     * สร้างและจัดวางตำแหน่ง UI ทั้งหมด
     */
    private void initComponents() {
        // ตั้งค่าตัว Panel หลัก (ขนาด 760 x 600)
        setBackground(new Color(236, 238, 248));
        setLayout(null);
        setPreferredSize(new Dimension(760, 600));

        // 1. หัวข้อ "Register Course"
        lblTitle = new JLabel("Register Course");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lblTitle.setForeground(new Color(37, 51, 91));
        lblTitle.setBounds(30, 30, 440, 50);
        add(lblTitle);

        // 2. กรอบการ์ดสีขาวสำหรับใส่ตารางและปุ่ม
        tableContainerPanel = new JPanel();
        tableContainerPanel.setBackground(Color.WHITE);
        tableContainerPanel.setLayout(null);
        tableContainerPanel.setBounds(20, 110, 710, 450);

        // หัวข้อตาราง "Available Courses"
        lblTableTitle = new JLabel("Available Courses");
        lblTableTitle.setFont(new Font("Leelawadee UI", Font.PLAIN, 20));
        lblTableTitle.setForeground(new Color(37, 51, 91));
        lblTableTitle.setBounds(21, 8, 215, 30);
        tableContainerPanel.add(lblTableTitle);

        // สร้าง JTable (7 คอลัมน์)
        String[] columns = {"Course ID", "Course Name", "Credit", "Section", "Teacher", "Status", "Seat"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                switch (columnIndex) {
                    case 0: return Integer.class;
                    case 1: return String.class;
                    case 2: return Integer.class;
                    case 3: return Integer.class;
                    case 4: return String.class;
                    case 5: return Integer.class;
                    case 6: return Integer.class;
                    default: return Object.class;
                }
            }
        };

        tblCourses = new JTable(model);
        tblCourses.setRowHeight(61); // ความสูงแถวตามต้นฉบับ

        scrollPane = new JScrollPane(tblCourses);
        scrollPane.setBounds(21, 50, 669, 328);
        tableContainerPanel.add(scrollPane);

        // ปุ่ม Register
        btnRegister = new JButton("Register");
        btnRegister.setBackground(new Color(83, 96, 134));
        btnRegister.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        btnRegister.setForeground(Color.WHITE);
        btnRegister.setFocusPainted(false);
        btnRegister.setBounds(292, 396, 130, 35);
        btnRegister.addActionListener(this::btnRegisterActionPerformed);
        tableContainerPanel.add(btnRegister);

        add(tableContainerPanel);
    }

    // ===================== Event Handlers =====================

    private void btnRegisterActionPerformed(ActionEvent evt) {
        // สลับไปแสดงหน้า RegisClassWindow ภายในพื้นที่ Panel นี้
        removeAll();
        setLayout(new BorderLayout());
        add(new RegisClassWindow(), BorderLayout.CENTER);
        revalidate();
        repaint();
    }
}


