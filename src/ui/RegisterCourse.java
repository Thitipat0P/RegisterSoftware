package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controller.EnrollmentController;
import model.Course;
import model.Student;
import repository.CourseRepository;
import repository.EnrollmentRepository;
import service.ScheduleService;

/**
 * หน้า Sub-panel สำหรับแสดงรายวิชาที่เปิดให้ลงทะเบียน (Register Course)
 * แสดงเวลาเรียน, ช่วงเวลาเปิดลงทะเบียน และแจ้งเตือนวิชาที่เวลาชนกัน
 */
public class RegisterCourse extends JPanel {

    private final Student student;
    private final Runnable onChanged;
    private final CourseRepository courseRepository = new CourseRepository();
    private final EnrollmentRepository enrollmentRepository = new EnrollmentRepository();
    private final ScheduleService scheduleService = new ScheduleService();
    private final EnrollmentController enrollmentController = new EnrollmentController();

    private JTable tblCourses;

    /** @param onChanged called after a successful registration so the caller can refresh the view. */
    public RegisterCourse(Student student, Runnable onChanged) {
        this.student = student;
        this.onChanged = onChanged;
        initComponents();
        loadData();
    }

    private void initComponents() {
        setBackground(new Color(236, 238, 248));
        setLayout(null);
        setPreferredSize(new Dimension(760, 600));

        JLabel lblTitle = new JLabel("Register Course");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lblTitle.setForeground(new Color(37, 51, 91));
        lblTitle.setBounds(30, 30, 440, 50);
        add(lblTitle);

        // แสดงสถานะช่วงเวลาลงทะเบียน (เขียว = เปิด, แดง = ปิด)
        boolean open = scheduleService.isRegistrationOpen();
        JLabel lblPeriod = new JLabel(scheduleService.getRegistrationStatusText());
        lblPeriod.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblPeriod.setForeground(open ? new Color(34, 139, 34) : new Color(200, 30, 30));
        lblPeriod.setBounds(32, 80, 600, 22);
        add(lblPeriod);

        JPanel tableContainerPanel = new JPanel();
        tableContainerPanel.setBackground(Color.WHITE);
        tableContainerPanel.setLayout(null);
        tableContainerPanel.setBounds(20, 110, 710, 450);

        JLabel lblTableTitle = new JLabel("Available Courses");
        lblTableTitle.setFont(new Font("Leelawadee UI", Font.PLAIN, 20));
        lblTableTitle.setForeground(new Color(37, 51, 91));
        lblTableTitle.setBounds(21, 8, 215, 30);
        tableContainerPanel.add(lblTableTitle);

        String[] columns = {"Course ID", "Course Name", "Credit", "Class Time", "Note"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tblCourses = new JTable(model);
        tblCourses.setRowHeight(40);
        tblCourses.getColumnModel().getColumn(0).setPreferredWidth(70);
        tblCourses.getColumnModel().getColumn(1).setPreferredWidth(190);
        tblCourses.getColumnModel().getColumn(2).setPreferredWidth(50);
        tblCourses.getColumnModel().getColumn(3).setPreferredWidth(120);
        tblCourses.getColumnModel().getColumn(4).setPreferredWidth(140);

        JScrollPane scrollPane = new JScrollPane(tblCourses);
        scrollPane.setBounds(21, 50, 669, 328);
        tableContainerPanel.add(scrollPane);

        JButton btnRegister = new JButton("Register");
        btnRegister.setBackground(new Color(83, 96, 134));
        btnRegister.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        btnRegister.setForeground(Color.WHITE);
        btnRegister.setFocusPainted(false);
        btnRegister.setBounds(292, 396, 130, 35);
        btnRegister.addActionListener(this::btnRegisterActionPerformed);
        tableContainerPanel.add(btnRegister);

        add(tableContainerPanel);
    }

    private void loadData() {
        DefaultTableModel model = (DefaultTableModel) tblCourses.getModel();
        model.setRowCount(0);

        List<String> enrolledIds = enrollmentRepository.getEnrolledCourseIds(student.getId());
        List<Course> enrolled = scheduleService.getEnrolledCourses(student.getId());

        for (Course course : courseRepository.getAllCourses()) {
            if (!enrolledIds.contains(course.getCourseId())) {
                Course conflict = scheduleService.findConflict(enrolled, course);
                String note = conflict == null ? "" : "Conflicts with " + conflict.getCourseId();
                model.addRow(new Object[]{course.getCourseId(), course.getName(),
                        course.getCredit(), course.getScheduleText(), note});
            }
        }
    }

    private void btnRegisterActionPerformed(ActionEvent evt) {
        int row = tblCourses.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Please select a course first.", "Register", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String courseId = tblCourses.getValueAt(row, 0).toString();
        String courseName = tblCourses.getValueAt(row, 1).toString();
        String credit = tblCourses.getValueAt(row, 2).toString();
        String time = tblCourses.getValueAt(row, 3).toString();

        int confirm = JOptionPane.showConfirmDialog(this,
                "Register for " + courseId + " - " + courseName
                        + " (" + credit + " credits, " + time + ")?",
                "Confirm Registration", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                enrollmentController.register(student.getId(), courseId);
                JOptionPane.showMessageDialog(this, "Course registered successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                if (onChanged != null) {
                    onChanged.run();
                } else {
                    loadData();
                }
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Cannot register", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
