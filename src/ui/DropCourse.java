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

import model.Course;
import model.Student;
import repository.CourseRepository;
import repository.EnrollmentRepository;

/**
 * หน้า Sub-panel สำหรับแสดงรายวิชาที่ต้องการถอน (Drop Course)
 */
public class DropCourse extends JPanel {

    private final Student student;
    private final Runnable onChanged;
    private final CourseRepository courseRepository = new CourseRepository();
    private final EnrollmentRepository enrollmentRepository = new EnrollmentRepository();

    private JTable tblCourses;

    /** @param onChanged called after a successful withdrawal so the caller can refresh the view. */
    public DropCourse(Student student, Runnable onChanged) {
        this.student = student;
        this.onChanged = onChanged;
        initComponents();
        loadData();
    }

    private void initComponents() {
        setBackground(new Color(236, 238, 248));
        setLayout(null);
        setPreferredSize(new Dimension(760, 600));

        JLabel lblTitle = new JLabel("Drop Course");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lblTitle.setForeground(new Color(37, 51, 91));
        lblTitle.setBounds(30, 30, 440, 50);
        add(lblTitle);

        JPanel tableContainerPanel = new JPanel();
        tableContainerPanel.setBackground(Color.WHITE);
        tableContainerPanel.setLayout(null);
        tableContainerPanel.setBounds(20, 110, 710, 450);

        JLabel lblTableTitle = new JLabel("My Registered Courses");
        lblTableTitle.setFont(new Font("Leelawadee UI", Font.PLAIN, 20));
        lblTableTitle.setForeground(new Color(37, 51, 91));
        lblTableTitle.setBounds(21, 8, 215, 30);
        tableContainerPanel.add(lblTableTitle);

        String[] columns = {"Course ID", "Course Name", "Credit"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tblCourses = new JTable(model);
        tblCourses.setRowHeight(40);

        JScrollPane scrollPane = new JScrollPane(tblCourses);
        scrollPane.setBounds(21, 50, 669, 328);
        tableContainerPanel.add(scrollPane);

        JButton btnDrop = new JButton("Drop");
        btnDrop.setBackground(new Color(83, 96, 134));
        btnDrop.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        btnDrop.setForeground(Color.WHITE);
        btnDrop.setFocusPainted(false);
        btnDrop.setBounds(290, 396, 130, 35);
        btnDrop.addActionListener(this::btnDropActionPerformed);
        tableContainerPanel.add(btnDrop);

        add(tableContainerPanel);
    }

    private void loadData() {
        DefaultTableModel model = (DefaultTableModel) tblCourses.getModel();
        model.setRowCount(0);

        List<String> enrolledIds = enrollmentRepository.getEnrolledCourseIds(student.getId());
        for (Course course : courseRepository.getAllCourses()) {
            if (enrolledIds.contains(course.getCourseId())) {
                model.addRow(new Object[]{course.getCourseId(), course.getName(), course.getCredit()});
            }
        }
    }

    private void btnDropActionPerformed(ActionEvent evt) {
        int row = tblCourses.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Please select a course first.", "Drop", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String courseId = tblCourses.getValueAt(row, 0).toString();
        String courseName = tblCourses.getValueAt(row, 1).toString();

        int confirm = JOptionPane.showConfirmDialog(this,
                "Withdraw from " + courseId + " - " + courseName + "?",
                "Confirm Withdrawal", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (enrollmentRepository.withdraw(student.getId(), courseId)) {
                JOptionPane.showMessageDialog(this, "Course withdrawn successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                if (onChanged != null) {
                    onChanged.run();
                } else {
                    loadData();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Could not withdraw this course.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
