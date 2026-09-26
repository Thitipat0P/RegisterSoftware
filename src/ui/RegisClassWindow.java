package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * หน้า Sub-panel สำหรับเลือกกลุ่มเรียน (Section) / ยืนยันการลงทะเบียน
 */
public class RegisClassWindow extends JPanel {

    // ===================== ตัวแปร Component =====================
    private JLabel lblTitle;
    private JComboBox<String> cmbSection;
    private JButton btnBack;

    /**
     * คอนสตรัคเตอร์สร้างหน้า RegisClassWindow
     */
    public RegisClassWindow() {
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
        lblTitle = new JLabel("Select Course Section");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lblTitle.setForeground(new Color(37, 51, 91));
        lblTitle.setBounds(30, 30, 440, 50);
        add(lblTitle);

        // ตัวเลือก ComboBox
        String[] sections = {"Section 1", "Section 2", "Section 3", "Section 4"};
        cmbSection = new JComboBox<>(sections);
        cmbSection.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        cmbSection.setBounds(320, 150, 150, 35);
        add(cmbSection);

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
        // ย้อนกลับไปหน้า RegisterCourse
        removeAll();
        setLayout(new BorderLayout());
        add(new RegisterCourse(), BorderLayout.CENTER);
        revalidate();
        repaint();
    }
}












