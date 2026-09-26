package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * หน้า Sub-panel สำหรับยืนยันการถอนรายวิชา (Drop Window)
 */
public class DropWindow extends JPanel {

    // ===================== ตัวแปร Component =====================
    private JLabel lblTitle;
    private JButton btnBack;

    /**
     * คอนสตรัคเตอร์สร้างหน้า DropWindow
     */
    public DropWindow() {
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

        // หัวข้อหน้าจอ (เพิ่มให้สอดคล้องกับธีมของหน้าอื่นๆ)
        lblTitle = new JLabel("Drop Course");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lblTitle.setForeground(new Color(37, 51, 91));
        lblTitle.setBounds(30, 30, 440, 50);
        add(lblTitle);

        // ปุ่ม Back
        btnBack = new JButton("Back");
        btnBack.setBackground(new Color(83, 96, 134));
        btnBack.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        btnBack.setForeground(Color.WHITE);
        btnBack.setFocusPainted(false);
        btnBack.setBounds(320, 220, 150, 35);
        btnBack.addActionListener(this::btnBackActionPerformed);
        add(btnBack);
    }

    // ===================== Event Handlers =====================

    private void btnBackActionPerformed(ActionEvent evt) {
        // ย้อนกลับไปหน้า DropCourse
        removeAll();
        setLayout(new BorderLayout());
        add(new DropCourse(), BorderLayout.CENTER);
        revalidate();
        repaint();
    }
}









