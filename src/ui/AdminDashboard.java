package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import controller.AdminController;
import model.Admin;
import model.Course;
import model.RegistrationPeriod;
import repository.CourseRepository;
import repository.RegistrationPeriodRepository;
import service.ScheduleService;

public class AdminDashboard extends JPanel {

    private static final Color NAVY = new Color(37, 51, 91);
    private static final Color BTN = new Color(83, 96, 134);
    private static final Color BG = new Color(236, 238, 248);

    private final Admin admin;
    private final AdminController controller = new AdminController();
    private final CourseRepository courseRepository = new CourseRepository();
    private final RegistrationPeriodRepository periodRepository = new RegistrationPeriodRepository();
    private final JPanel content = new JPanel(new BorderLayout());

    // หน้า Manage Courses
    private JTable tblCourses;
    private JTextField txtId;
    private JTextField txtName;
    private JTextField txtCredit;
    private JTextField txtStart;
    private JTextField txtEnd;
    private JComboBox<String> cmbDay;

    // หน้า Registration Period
    private JTextField txtOpen;
    private JTextField txtClose;

    public AdminDashboard(Admin admin) {
        this.admin = admin;
        setLayout(null);
        setPreferredSize(new Dimension(970, 600));

        // ------------------ Side Panel ------------------
        JPanel side = new JPanel(null);
        side.setBackground(NAVY);
        side.setBounds(0, 0, 210, 600);

        JLabel lblTitle = new JLabel("<html>Admin<br>Panel</html>");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setBounds(20, 30, 170, 70);
        side.add(lblTitle);

        JLabel lblName = new JLabel(admin.getName());
        lblName.setForeground(Color.WHITE);
        lblName.setBounds(20, 100, 170, 20);
        side.add(lblName);

        side.add(createMenuButton("Manage Courses", 130, this::btnManageCoursesActionPerformed));
        side.add(createMenuButton("Registration Period", 190, this::btnPeriodActionPerformed));
        side.add(createMenuButton("Logout", 250, this::btnLogoutActionPerformed));
        add(side);

        // ------------------ Main Content ------------------
        content.setBounds(210, 0, 760, 600);
        add(content);
        showPanel(createCoursePanel());
    }

    // ===================== ตัวช่วยสร้างหน้าจอ =====================

    private JButton createMenuButton(String text, int y, ActionListener listener) {
        JButton b = createButton(text, 20, y, 170, 40);
        b.addActionListener(listener);
        return b;
    }

    private JButton createButton(String text, int x, int y, int w, int h) {
        JButton b = new JButton(text);
        b.setBackground(BTN);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBounds(x, y, w, h);
        return b;
    }

    /** สร้างหน้าเปล่าที่มีหัวข้อใหญ่ด้านบน */
    private JPanel createPage(String title) {
        JPanel p = new JPanel(null);
        p.setBackground(BG);
        JLabel lbl = new JLabel(title);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lbl.setForeground(NAVY);
        lbl.setBounds(30, 30, 500, 50);
        p.add(lbl);
        return p;
    }

    private void showPanel(JPanel p) {
        content.removeAll();
        content.add(p, BorderLayout.CENTER);
        content.revalidate();
        content.repaint();
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    // ===================== ปุ่มเมนูด้านซ้าย =====================

    private void btnManageCoursesActionPerformed(ActionEvent evt) {
        showPanel(createCoursePanel());
    }

    private void btnPeriodActionPerformed(ActionEvent evt) {
        showPanel(createPeriodPanel());
    }

    private void btnLogoutActionPerformed(ActionEvent evt) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (frame != null) {
            frame.setTitle("Course Registration - Login");
            frame.setContentPane(new LoginGUI());
            frame.revalidate();
            frame.repaint();
        }
    }

    // ===================== หน้า Manage Courses =====================

    private JPanel createCoursePanel() {
        JPanel p = createPage("Manage Courses");

        // ตารางรายวิชา
        DefaultTableModel model = new DefaultTableModel(new String[]{"Course ID", "Name", "Credit", "Class Time"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tblCourses = new JTable(model);
        tblCourses.setRowHeight(30);
        JScrollPane scrollPane = new JScrollPane(tblCourses);
        scrollPane.setBounds(20, 90, 720, 300);
        p.add(scrollPane);

        // ฟอร์มกรอกข้อมูลวิชาใหม่
        txtId = new JTextField();
        txtName = new JTextField();
        txtCredit = new JTextField();
        txtStart = new JTextField("09:00");
        txtEnd = new JTextField("12:00");
        cmbDay = new JComboBox<>(new String[]{"TBA", "MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"});

        JPanel form = new JPanel(new GridLayout(2, 6, 6, 2));
        form.setOpaque(false);
        form.setBounds(20, 400, 720, 50);
        form.add(new JLabel("ID"));
        form.add(new JLabel("Name"));
        form.add(new JLabel("Credit"));
        form.add(new JLabel("Day"));
        form.add(new JLabel("Start"));
        form.add(new JLabel("End"));
        form.add(txtId);
        form.add(txtName);
        form.add(txtCredit);
        form.add(cmbDay);
        form.add(txtStart);
        form.add(txtEnd);
        p.add(form);

        // ปุ่ม Add / Delete
        JButton btnAdd = createButton("Add", 230, 470, 130, 35);
        btnAdd.addActionListener(this::btnAddActionPerformed);
        p.add(btnAdd);

        JButton btnDelete = createButton("Delete", 390, 470, 130, 35);
        btnDelete.addActionListener(this::btnDeleteActionPerformed);
        p.add(btnDelete);

        loadCourses();
        return p;
    }

    private void loadCourses() {
        DefaultTableModel model = (DefaultTableModel) tblCourses.getModel();
        model.setRowCount(0);
        for (Course c : courseRepository.getAllCourses()) {
            model.addRow(new Object[]{c.getCourseId(), c.getName(), c.getCredit(), c.getScheduleText()});
        }
    }

    private void btnAddActionPerformed(ActionEvent evt) {
        try {
            controller.addCourse(txtId.getText(), txtName.getText(), txtCredit.getText(),
                    (String) cmbDay.getSelectedItem(), txtStart.getText(), txtEnd.getText());
            txtId.setText("");
            txtName.setText("");
            txtCredit.setText("");
            loadCourses();
        } catch (IllegalArgumentException e) {
            showError(e.getMessage());
        }
    }

    private void btnDeleteActionPerformed(ActionEvent evt) {
        int row = tblCourses.getSelectedRow();
        if (row < 0) {
            showError("Please select a course first.");
            return;
        }

        String courseId = tblCourses.getValueAt(row, 0).toString();
        int confirm = JOptionPane.showConfirmDialog(this, "Delete " + courseId + "?",
                "Confirm", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                controller.deleteCourse(courseId);
                loadCourses();
            } catch (IllegalArgumentException e) {
                showError(e.getMessage());
            }
        }
    }

    // ===================== หน้า Registration Period =====================

    private JPanel createPeriodPanel() {
        JPanel p = createPage("Registration Period");

        JLabel lblStatus = new JLabel(new ScheduleService().getRegistrationStatusText());
        lblStatus.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblStatus.setBounds(32, 85, 600, 22);
        p.add(lblStatus);

        // ค่าเดิมในไฟล์ (ถ้ามี) ใส่ไว้ในช่องให้ก่อน
        RegistrationPeriod current = periodRepository.getPeriod();
        txtOpen = new JTextField();
        txtClose = new JTextField();
        if (current != null) {
            txtOpen.setText(current.getOpenAt().toString());
            txtClose.setText(current.getCloseAt().toString());
        }

        JLabel lblOpen = new JLabel("Open at (yyyy-MM-ddTHH:mm)");
        lblOpen.setBounds(32, 140, 300, 20);
        txtOpen.setBounds(32, 165, 260, 35);

        JLabel lblClose = new JLabel("Close at (yyyy-MM-ddTHH:mm)");
        lblClose.setBounds(32, 220, 300, 20);
        txtClose.setBounds(32, 245, 260, 35);

        p.add(lblOpen);
        p.add(txtOpen);
        p.add(lblClose);
        p.add(txtClose);

        JButton btnSave = createButton("Save", 32, 305, 130, 35);
        btnSave.addActionListener(this::btnSaveActionPerformed);
        p.add(btnSave);

        return p;
    }

    private void btnSaveActionPerformed(ActionEvent evt) {
        try {
            controller.setPeriod(txtOpen.getText(), txtClose.getText());
            JOptionPane.showMessageDialog(this, "Saved.");
            showPanel(createPeriodPanel()); // โหลดหน้าใหม่เพื่ออัปเดตข้อความสถานะ
        } catch (IllegalArgumentException e) {
            showError(e.getMessage());
        }
    }
}
