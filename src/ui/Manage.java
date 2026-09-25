package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Manage extends JPanel {

    // ===================== ตัวแปร Component =====================
    private JButton btnAddCourse;

    /**
     * คอนสตรัคเตอร์สร้างหน้า Manage Courses
     */
    public Manage() {
        initComponents();
    }

    /**
     * สร้างและจัดวางตำแหน่ง UI ทั้งหมด
     */
    private void initComponents() {

        // กำหนดขนาด panel รวม (760 x 600) ให้เท่ากับหน้า Dashboard
        setLayout(null);
        setPreferredSize(new Dimension(760, 600));
        setBackground(new Color(236, 238, 248));

        // ------------------ ปุ่ม Add Course ------------------
        btnAddCourse = new JButton("add course");
        btnAddCourse.setBounds(620, 50, 110, 30);
        btnAddCourse.addActionListener(this::btnAddCourseActionPerformed);

        add(btnAddCourse);
    }

    // ===================== Event Handlers =====================

    private void btnAddCourseActionPerformed(ActionEvent evt) {
        // TODO: ใส่ Logic เมื่อกดปุ่ม add course (เช่น เปิด Dialog หรือ Form เพิ่มคอร์ส)
    }
}