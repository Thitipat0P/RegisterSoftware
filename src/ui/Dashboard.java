package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.Course;
import model.Student;
import repository.CourseRepository;
import repository.EnrollmentRepository;

/**
 * หน้า Sub-panel สำหรับแสดง Dashboard ของนักศึกษา
 * แสดง: วิชาที่ลงทะเบียน, เวลาเรียน, เวลาที่ลงทะเบียน
 */
public class Dashboard extends JPanel {

    private final Student student; // นักศึกษาที่ login อยู่
    private final CourseRepository courseRepository = new CourseRepository();
    private final EnrollmentRepository enrollmentRepository = new EnrollmentRepository();

    private JTable tblCourses;         // ตารางวิชาที่ลงทะเบียนแล้ว
    private JLabel lblTotalCredits;    // ป้ายแสดงหน่วยกิตรวม

    public Dashboard(Student student) {
        this.student = student;
        initComponents(); // สร้างหน้าจอ
        loadData();       // โหลดข้อมูลลงตาราง
    }

    /** สร้างและจัดวางคอมโพเนนต์ทั้งหมด */
    private void initComponents() {
        setBackground(new Color(236, 238, 248));
        setLayout(null);
        setPreferredSize(new Dimension(760, 600));

        // หัวข้อหน้า
        JLabel lblTitle = new JLabel("Student Dashboard");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lblTitle.setForeground(new Color(37, 51, 91));
        lblTitle.setBounds(30, 30, 440, 40);
        add(lblTitle);

        // ข้อความต้อนรับ แสดงชื่อนักศึกษา
        JLabel lblWelcome = new JLabel("Welcome, " + student.getName());
        lblWelcome.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        lblWelcome.setForeground(new Color(86, 101, 144));
        lblWelcome.setBounds(30, 70, 400, 30);
        add(lblWelcome);

        // กรอบสีขาวที่ครอบตาราง
        JPanel tableContainerPanel = new JPanel();
        tableContainerPanel.setBackground(Color.WHITE);
        tableContainerPanel.setLayout(null);
        tableContainerPanel.setBounds(20, 110, 710, 450);

        JLabel lblTableTitle = new JLabel("My Registered Courses");
        lblTableTitle.setFont(new Font("Leelawadee UI", Font.PLAIN, 20));
        lblTableTitle.setForeground(new Color(37, 51, 91));
        lblTableTitle.setBounds(20, 10, 300, 30);
        tableContainerPanel.add(lblTableTitle);

        // ตารางแสดงวิชา (แก้ไขช่องในตารางไม่ได้)
        String[] columns = {"Course ID", "Course Name", "Credit", "Class Time", "Registered At"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tblCourses = new JTable(model);
        tblCourses.setRowHeight(40);
        // กำหนดความกว้างของแต่ละคอลัมน์
        tblCourses.getColumnModel().getColumn(0).setPreferredWidth(70);
        tblCourses.getColumnModel().getColumn(1).setPreferredWidth(190);
        tblCourses.getColumnModel().getColumn(2).setPreferredWidth(50);
        tblCourses.getColumnModel().getColumn(3).setPreferredWidth(120);
        tblCourses.getColumnModel().getColumn(4).setPreferredWidth(140);

        JScrollPane scrollPane = new JScrollPane(tblCourses);
        scrollPane.setBounds(20, 50, 670, 340);
        tableContainerPanel.add(scrollPane);

        // ป้ายแสดงหน่วยกิตรวม
        lblTotalCredits = new JLabel("Total Credits: 0");
        lblTotalCredits.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTotalCredits.setForeground(new Color(37, 51, 91));
        lblTotalCredits.setBounds(520, 400, 170, 30);
        tableContainerPanel.add(lblTotalCredits);

        add(tableContainerPanel);
    }

    /** โหลดวิชาที่นักศึกษาลงทะเบียนไว้มาแสดง พร้อมคำนวณหน่วยกิตรวม */
    private void loadData() {
        DefaultTableModel model = (DefaultTableModel) tblCourses.getModel();
        model.setRowCount(0); // ล้างตารางก่อน

        // รหัสวิชาที่นักศึกษาลงทะเบียนอยู่
        List<String> enrolledIds = enrollmentRepository.getEnrolledCourseIds(student.getId());
        int totalCredits = 0;

        // วนดูทุกวิชา แล้วเลือกเฉพาะวิชาที่ลงทะเบียนไว้
        for (Course course : courseRepository.getAllCourses()) {
            if (enrolledIds.contains(course.getCourseId())) {
                String registeredAt = enrollmentRepository.getEnrolledAt(student.getId(), course.getCourseId());
                model.addRow(new Object[]{
                        course.getCourseId(), course.getName(), course.getCredit(),
                        course.getScheduleText(), registeredAt});
                totalCredits += course.getCredit();
            }
        }

        lblTotalCredits.setText("Total Credits: " + totalCredits);
    }
}
