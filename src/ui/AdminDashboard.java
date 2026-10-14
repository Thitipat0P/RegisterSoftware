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
 
    // ข้อมูล Admin ที่ล็อกอินเข้ามา และส่วนเชื่อมโยง Logic/Data
    private final Admin admin; // เก็บว่าใครล็อกอินอยู่ ใช้แสดงชื่อ
    private final AdminController controller = new AdminController(); // ตัวจัดการ logic เช่น เพิ่ม/ลบวิชา
    private final CourseRepository courseRepository = new CourseRepository(); // ตัวดึงข้อมูลวิชาจากที่เก็บข้อมูล
    private final RegistrationPeriodRepository periodRepository = new RegistrationPeriodRepository(); // ตัวดึงข้อมูลช่วงเวลาลงทะเบียน
 
    
    private final JPanel content = new JPanel(new BorderLayout());
 
    // หน้า Manage Courses
    private JTable tblCourses;
    private JTextField txtId;
    private JTextField txtName;
    private JTextField txtCredit;
    private JTextField txtStart;
    private JTextField txtEnd;
    private JComboBox<String> cmbDay; // กล่องเลือกวันเรียน
 
    // หน้า Registration Period
    private JTextField txtOpen; // ช่องกรอกวันเวลาที่เปิดลงทะเบียน
    private JTextField txtClose; // ช่องกรอกวันเวลาที่ปิดลงทะเบียน
 
    // ทำงานทันทีตอนสร้างหน้านี้ขึ้นมา รับ Admin ที่ล็อกอินเข้ามา
    public AdminDashboard(Admin admin) {
        this.admin = admin;
        setLayout(null);
        setPreferredSize(new Dimension(970, 600));
 
        // แถบเมนูด้านซ้าย
        JPanel side = new JPanel(null);
        side.setBackground(NAVY);
        side.setBounds(0, 0, 210, 600);
 
        // หัวข้อ Admin Panel
        JLabel lblTitle = new JLabel("Admin Panel");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setBounds(20, 45, 170, 70);
        side.add(lblTitle);
 
        // แสดงชื่อ Admin ที่ล็อกอินอยู่
        JLabel lblName = new JLabel(admin.getName());
        lblName.setForeground(Color.WHITE);
        lblName.setBounds(20, 100, 170, 20);
        side.add(lblName);
 
        // ปุ่มเมนู 3 ปุ่มด้านซ้าย
        side.add(createMenuButton("Manage Courses", 130, this::btnManageCoursesActionPerformed));
        side.add(createMenuButton("Registration Period", 190, this::btnPeriodActionPerformed));
        side.add(createMenuButton("Logout", 250, this::btnLogoutActionPerformed));
        add(side);
 
        // พื้นที่ด้านขวา
        content.setBounds(210, 0, 760, 600);
        add(content);
 
        // เปิดมาครั้งแรกให้แสดงหน้า Manage Courses เป็นหน้าเริ่มต้น
        showPanel(createCoursePanel());
    }
 
 
    // สร้างปุ่มเมนูด้านซ้ายพร้อมผูกให้ทำงานเมื่อถูกกด
    private JButton createMenuButton(String text, int y, ActionListener listener) {
        JButton b = createButton(text, 20, y, 170, 40);
        b.addActionListener(listener);
        return b;
    }
 
    private JButton createButton(String text, int x, int y, int w, int h) {
        JButton b = new JButton(text);
        b.setBackground(BTN);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false); // ปิดกรอบรอบๆข้อความ
        b.setBounds(x, y, w, h);
        return b;
    }
 
    // สร้างหน้าเปล่าที่มีหัวข้อใหญ่ด้านบน
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
 
    // สลับหน้าที่แสดงในพื้นที่ content
    private void showPanel(JPanel p) {
        content.removeAll();// เอาหน้าเก่าออกให้หมด
        content.add(p, BorderLayout.CENTER);// ใส่หน้าใหม่ไว้ตรงกลาง
        content.revalidate();
        content.repaint();
    }
 
    // แสดงกล่องข้อความแจ้ง Error
    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
 
 
    // ปุ่ม Manage Courses
    private void btnManageCoursesActionPerformed(ActionEvent evt) {
        showPanel(createCoursePanel());
    }
 
    // ปุ่ม Registration Period
    private void btnPeriodActionPerformed(ActionEvent evt) {
        showPanel(createPeriodPanel());
    }
 
    // ปุ่ม Logout
    private void btnLogoutActionPerformed(ActionEvent evt) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (frame != null) {
            frame.setTitle("Course Registration - Login");
            frame.setContentPane(new LoginGUI()); // เปลี่ยนเป็นหน้า Login
            frame.revalidate();
            frame.repaint();
        }
    }
 
    // หน้า Manage Courses
    private JPanel createCoursePanel() {
        JPanel p = createPage("Manage Courses");
 
        // ตารางรายวิชา
        DefaultTableModel model = new DefaultTableModel(new String[]{"Course ID", "Name", "Credit", "Class Time"}, 0) {
            // ทำให้แก้ไขข้อมูลไม่ได้
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tblCourses = new JTable(model);
        tblCourses.setRowHeight(30);
        // ครอบตารางด้วย JScrollPane เพื่อให้เลื่อนขึ้นลงได้เมื่อข้อมูลเยอะ และเพื่อให้หัวตารางแสดงขึ้นมา
        JScrollPane scrollPane = new JScrollPane(tblCourses);
        scrollPane.setBounds(20, 90, 720, 300);
        p.add(scrollPane);
 
        // ฟอร์มกรอกข้อมูลวิชาใหม่
        txtId = new JTextField();
        txtName = new JTextField();
        txtCredit = new JTextField();
        txtStart = new JTextField("09:00"); // ใส่ค่าเริ่มต้นไว้ให้จะได้ไม่ต้องพิมพ์ทุกครั้ง
        txtEnd = new JTextField("12:00");
        cmbDay = new JComboBox<>(new String[]{"TBA", "MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"}); 
 
        // แถวบนเป็นป้ายชื่อ แถวล่างเป็นช่องกรอก
        JPanel form = new JPanel(new GridLayout(2, 6, 6, 2));
        form.setBounds(20, 400, 720, 50);
 
        //แถว 1
        form.add(new JLabel("ID"));
        form.add(new JLabel("Name"));
        form.add(new JLabel("Credit"));
        form.add(new JLabel("Day"));
        form.add(new JLabel("Start"));
        form.add(new JLabel("End"));
        // แถวที่ 2 ช่องกรอก
        form.add(txtId);
        form.add(txtName);
        form.add(txtCredit);
        form.add(cmbDay);
        form.add(txtStart);
        form.add(txtEnd);
        p.add(form);
 
        // ปุ่ม Add Delete
        JButton btnAdd = createButton("Add", 230, 470, 130, 35);
        btnAdd.addActionListener(this::btnAddActionPerformed);
        p.add(btnAdd);
 
        JButton btnDelete = createButton("Delete", 390, 470, 130, 35);
        btnDelete.addActionListener(this::btnDeleteActionPerformed);
        p.add(btnDelete);
 
        loadCourses(); // ดึงข้อมูลวิชามาใส่ตารางตอนเปิดหน้า
        return p;
    }
 
    // โหลดรายวิชาทั้งหมดมาแสดงในตาราง
    private void loadCourses() {
        DefaultTableModel model = (DefaultTableModel) tblCourses.getModel();
        model.setRowCount(0); // ล้างแถวเก่าทิ้งก่อนไม่งั้นข้อมูลจะซ้ำ
        // วนลูปทีละวิชา แล้วเพิ่มเป็นแถวใหม่ในตาราง
        for (Course c : courseRepository.getAllCourses()) {
            model.addRow(new Object[]{c.getCourseId(), c.getName(), c.getCredit(), c.getScheduleText()});
        }
    }
 
    // กดปุ่ม Add เพิ่มวิชาใหม่
    private void btnAddActionPerformed(ActionEvent evt) {
        try {
            // ส่งค่าจากช่องกรอกทั้งหมดให้ controller ไปตรวจสอบและบันทึก
            // ถ้าข้อมูลไม่ถูกต้อง controller จะโยน IllegalArgumentException ออกมา
            controller.addCourse(txtId.getText(), txtName.getText(), txtCredit.getText(),
                    (String) cmbDay.getSelectedItem(), txtStart.getText(), txtEnd.getText());
            // เพิ่มสำเร็จ ล้างช่องกรอกให้พร้อมกรอกวิชาถัดไป
            txtId.setText("");
            txtName.setText("");
            txtCredit.setText("");
            loadCourses(); // รีเฟรชตารางให้เห็นวิชาที่พึ่งเพิ่ม
        } catch (IllegalArgumentException e) {
            // ข้อมูลไม่ถูกต้องแจ้งเตือนผู้ใช้
            showError(e.getMessage());
        }
    }
 
    // กดปุ่ม Delete ลบวิชาที่เลือกในตาราง
    private void btnDeleteActionPerformed(ActionEvent evt) {
        int row = tblCourses.getSelectedRow(); // ได้เลขแถวที่เลือก
        if (row < 0) {
            showError("Please select a course first.");
            return; // หยุดการทำงานตรงนี้เลย ไม่ต้องทำบรรทัดถัดไป
        }
 
        // ดึงรหัสวิชาจากคอลัมน์แรกของแถวที่เลือก
        String courseId = tblCourses.getValueAt(row, 0).toString();
        // ถามยืนยันก่อนลบ
        int confirm = JOptionPane.showConfirmDialog(this, "Delete " + courseId + "?",
                "Confirm", JOptionPane.YES_NO_OPTION);
 
        // ลบจริงเมื่อกด Yes
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                controller.deleteCourse(courseId);
                loadCourses(); // รีเฟรชตารางหลังลบ
            } catch (IllegalArgumentException e) {
                showError(e.getMessage());
            }
        }
    }
 
    // หน้า Registration Period
    private JPanel createPeriodPanel() {
        JPanel p = createPage("Registration Period");
 
        // ข้อความบอกสถานะปัจจุบัน เช่น ตอนนี้เปิดหรือปิดลงทะเบียนอยู่
        JLabel lblStatus = new JLabel(new ScheduleService().getRegistrationStatusText()); // ScheduleService เป็นตัวคำนวณ
        lblStatus.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblStatus.setBounds(32, 85, 600, 22);
        p.add(lblStatus);
 
        // ใส่ค่าเดิมในไฟล์
        RegistrationPeriod current = periodRepository.getPeriod();
        txtOpen = new JTextField();
        txtClose = new JTextField();
        if (current != null) { // ถ้าเคยตั้งค่าไว้แล้ว ค่อยเอามาแสดง ถ้ายังจะเป็น null
            txtOpen.setText(current.getOpenAt().toString());
            txtClose.setText(current.getCloseAt().toString());
        }
 
        // ช่องกรอกวันเวลาเปิด
        JLabel lblOpen = new JLabel("Open at (yyyy-MM-ddTHH:mm)"); // บอกปี เดือน วัน เวลา
        lblOpen.setBounds(32, 140, 300, 20);
        txtOpen.setBounds(32, 165, 260, 35);
 
        // ช่องกรอกวันเวลาปิด
        JLabel lblClose = new JLabel("Close at (yyyy-MM-ddTHH:mm)");
        lblClose.setBounds(32, 220, 300, 20);
        txtClose.setBounds(32, 245, 260, 35);
 
        p.add(lblOpen);
        p.add(txtOpen);
        p.add(lblClose);
        p.add(txtClose);
 
        // ปุ่ม Save
        JButton btnSave = createButton("Save", 32, 305, 130, 35);
        btnSave.addActionListener(this::btnSaveActionPerformed);
        p.add(btnSave);
 
        return p;
    }
 
    // กดปุ่ม Save บันทึกช่วงเวลาลงทะเบียน
    private void btnSaveActionPerformed(ActionEvent evt) {
        try {
            // ส่งวันเวลาที่กรอกให้ controller ตรวจสอบและบันทึก ถ้ารูปแบบผิดจะ error
            controller.setPeriod(txtOpen.getText(), txtClose.getText());
            JOptionPane.showMessageDialog(this, "Saved.");
            showPanel(createPeriodPanel()); // โหลดหน้าใหม่เพื่ออัปเดตข้อความสถานะ
        } catch (IllegalArgumentException e) {
            showError(e.getMessage());
        }
    }
}