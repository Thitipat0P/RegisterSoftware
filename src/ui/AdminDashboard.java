package ui;

import service.CourseManageService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AdminDashboard {
    
    private CourseManageService manageService;
    private JTable courseTable;
    private DefaultTableModel tableModel;

    public AdminDashboard() {
        manageService = new CourseManageService();
        setupUI();
    }

    private void setupUI() {
        JFrame frame = new JFrame("Admin Dashboard - จัดการระบบ");
        frame.setSize(750, 450);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // ================= ฝั่งซ้าย: ฟอร์มเพิ่มรายวิชา =================
        JLabel titleLabel = new JLabel("เพิ่มรายวิชาใหม่");
        titleLabel.setBounds(20, 10, 150, 25);
        frame.add(titleLabel);

        JLabel idLabel = new JLabel("รหัสวิชา:");
        idLabel.setBounds(20, 50, 80, 25);
        frame.add(idLabel);

        JTextField idText = new JTextField();
        idText.setBounds(100, 50, 150, 25);
        frame.add(idText);

        JLabel nameLabel = new JLabel("ชื่อวิชา:");
        nameLabel.setBounds(20, 90, 80, 25);
        frame.add(nameLabel);

        JTextField nameText = new JTextField();
        nameText.setBounds(100, 90, 150, 25);
        frame.add(nameText);

        JLabel creditLabel = new JLabel("หน่วยกิต:");
        creditLabel.setBounds(20, 130, 80, 25);
        frame.add(creditLabel);

        Integer[] credits = {1, 2, 3, 4};
        JComboBox<Integer> creditBox = new JComboBox<>(credits);
        creditBox.setBounds(100, 130, 150, 25);
        frame.add(creditBox);

        // ช่องใส่จำนวนที่นั่ง
        JLabel seatLabel = new JLabel("ที่นั่งรวม:");
        seatLabel.setBounds(20, 170, 80, 25);
        frame.add(seatLabel);

        JTextField seatText = new JTextField();
        seatText.setBounds(100, 170, 150, 25);
        frame.add(seatText);

        JButton saveButton = new JButton("บันทึก");
        saveButton.setBounds(100, 220, 100, 25);
        frame.add(saveButton);

        // ================= ฝั่งขวา: ตารางแสดงข้อมูลและลบ =================
        JLabel tableLabel = new JLabel("รายวิชาในระบบทั้งหมด");
        tableLabel.setBounds(300, 10, 200, 25);
        frame.add(tableLabel);

        // สร้างตาราง
        String[] columnNames = {"รหัสวิชา", "ชื่อวิชา", "หน่วยกิต", "จำนวนที่นั่ง"};
        tableModel = new DefaultTableModel(columnNames, 0);
        courseTable = new JTable(tableModel);
        
        // ใส่ตารางลงใน ScrollPane เพื่อให้เลื่อนดูได้
        JScrollPane scrollPane = new JScrollPane(courseTable);
        scrollPane.setBounds(300, 40, 400, 300);
        frame.add(scrollPane);

        JButton deleteButton = new JButton("ลบวิชาที่เลือก");
        deleteButton.setBounds(300, 350, 120, 25);
        frame.add(deleteButton);

        // โหลดข้อมูลเข้าตารางครั้งแรกตอนเปิดหน้าต่าง
        refreshTable();

        // ================= การทำงานของปุ่ม (Events) =================

        // 1. กดปุ่มบันทึก
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cId = idText.getText().trim();
                String cName = nameText.getText().trim();
                int cCredit = (int) creditBox.getSelectedItem();
                
                try {
                    int cSeats = Integer.parseInt(seatText.getText().trim());
                    String result = manageService.addCourse(cId, cName, cCredit, cSeats);

                    if (result.equals("SUCCESS")) {
                        JOptionPane.showMessageDialog(frame, "บันทึกรายวิชาสำเร็จ!");
                        idText.setText("");
                        nameText.setText("");
                        seatText.setText("");
                        refreshTable(); // อัปเดตตารางทันที
                    } else {
                        JOptionPane.showMessageDialog(frame, result, "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "กรุณากรอกจำนวนที่นั่งเป็นตัวเลขเท่านั้น!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // 2. กดปุ่มลบ
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = courseTable.getSelectedRow();
                
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(frame, "กรุณาคลิกเลือกวิชาที่ต้องการลบในตารางก่อน", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // ดึงรหัสวิชาจากคอลัมน์แรก (Index 0) ของแถวที่เลือก
                String cId = tableModel.getValueAt(selectedRow, 0).toString();
                
                // ยืนยันการลบ
                int confirm = JOptionPane.showConfirmDialog(frame, "คุณต้องการลบวิชา " + cId + " ใช่หรือไม่?", "ยืนยันการลบ", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    String result = manageService.deleteCourse(cId);
                    if (result.equals("SUCCESS")) {
                        refreshTable(); // อัปเดตตารางทันที
                    } else {
                        JOptionPane.showMessageDialog(frame, result, "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        frame.setVisible(true);
    }

    // เมธอดสำหรับดึงข้อมูลใหม่มาวาดลงในตาราง (ใช้ตอนเปิดโปรแกรม, หลังเพิ่ม, และหลังลบ)
    private void refreshTable() {
        tableModel.setRowCount(0); // ล้างข้อมูลเดิมในตารางก่อน
        List<String[]> courses = manageService.getAllCourses();
        for (String[] course : courses) {
            tableModel.addRow(course); // นำข้อมูลแถวใหม่ใส่ลงไป
        }
    }
}