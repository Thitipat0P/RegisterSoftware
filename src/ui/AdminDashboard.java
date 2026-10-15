package ui;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import controller.AdminController;
import model.Admin;
import model.Course;
import model.RegistrationPeriod;
import repository.CourseRepository;
import repository.RegistrationPeriodRepository;
import service.ScheduleService;

/**
 * หน้าหลักของผู้ดูแลระบบ (Admin) หลังล็อกอิน
 * มีเมนู: จัดการรายวิชา (เพิ่ม/ลบ), ตั้งช่วงเวลาลงทะเบียน, ออกจากระบบ
 */
public class AdminDashboard extends JPanel {

    // ชุดสีของหน้าจอ
    private static final Color NAVY = new Color(37, 51, 91);
    private static final Color BTN = new Color(83, 96, 134);
    private static final Color BG = new Color(236, 238, 248);

    private final Admin admin; // ผู้ดูแลที่ล็อกอินอยู่
    private final AdminController ctrl = new AdminController();
    private final JPanel content = new JPanel(new BorderLayout()); // พื้นที่แสดงเนื้อหาฝั่งขวา

    public AdminDashboard(Admin admin) {
        this.admin = admin;
        setLayout(null);
        setPreferredSize(new Dimension(970, 600));

        // ----- แผงเมนูด้านซ้าย -----
        JPanel side = new JPanel(null);
        side.setBackground(NAVY);
        side.setBounds(0, 0, 210, 600);
        JLabel t = new JLabel("<html>Admin<br>Panel</html>");
        t.setFont(new Font("Segoe UI", Font.BOLD, 24));
        t.setForeground(Color.WHITE);
        t.setBounds(20, 30, 170, 70);
        side.add(t);
        JLabel who = new JLabel(admin.getName()); // แสดงชื่อ Admin
        who.setForeground(Color.WHITE);
        who.setBounds(20, 100, 170, 20);
        side.add(who);
        side.add(menu("Manage Courses", 130, e -> show(coursePanel())));
        side.add(menu("Registration Period", 190, e -> show(periodPanel())));
        side.add(menu("Logout", 250, e -> logout()));
        add(side);

        // ----- พื้นที่เนื้อหา: เริ่มต้นที่หน้าจัดการรายวิชา -----
        content.setBounds(210, 0, 760, 600);
        add(content);
        show(coursePanel());
    }

    // ---------- helpers ----------

    /** สร้างปุ่มเมนูด้านซ้ายพร้อมผูก action */
    private JButton menu(String text, int y, ActionListener l) {
        JButton b = button(text, 20, y, 170, 40);
        b.addActionListener(l);
        return b;
    }

    /** สร้างปุ่มมาตรฐานของหน้านี้ (สี BTN ตัวอักษรขาว) */
    private JButton button(String text, int x, int y, int w, int h) {
        JButton b = new JButton(text);
        b.setBackground(BTN);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBounds(x, y, w, h);
        return b;
    }

    /** สร้าง panel เปล่าพร้อมหัวข้อใหญ่ สำหรับแต่ละหน้า */
    private JPanel page(String title) {
        JPanel p = new JPanel(null);
        p.setBackground(BG);
        JLabel l = new JLabel(title);
        l.setFont(new Font("Segoe UI", Font.BOLD, 36));
        l.setForeground(NAVY);
        l.setBounds(30, 30, 500, 50);
        p.add(l);
        return p;
    }

    /** เปลี่ยนเนื้อหาฝั่งขวาเป็น panel ที่ส่งเข้ามา */
    private void show(JPanel p) {
        content.removeAll();
        content.add(p, BorderLayout.CENTER);
        content.revalidate();
        content.repaint();
    }

    /** แสดงกล่องข้อความผิดพลาด */
    private void err(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /** ออกจากระบบ -> กลับหน้า Login */
    private void logout() {
        JFrame f = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (f != null) {
            f.setTitle("Course Registration - Login");
            f.setContentPane(new LoginGUI());
            f.revalidate();
            f.repaint();
        }
    }

    // ---------- Manage Courses ----------

    /** หน้าจัดการรายวิชา: ตารางรายวิชา + ฟอร์มเพิ่ม + ปุ่มลบ */
    private JPanel coursePanel() {
        JPanel p = page("Manage Courses");

        // ตารางแสดงรายวิชา (อ่านอย่างเดียว)
        DefaultTableModel m = new DefaultTableModel(new String[]{"Course ID", "Name", "Credit", "Class Time"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable tbl = new JTable(m);
        tbl.setRowHeight(30);
        JScrollPane sp = new JScrollPane(tbl);
        sp.setBounds(20, 90, 720, 300);
        p.add(sp);

        // reload: โหลดรายวิชาทั้งหมดจากไฟล์มาใส่ตารางใหม่
        Runnable reload = () -> {
            m.setRowCount(0);
            for (Course c : new CourseRepository().getAllCourses())
                m.addRow(new Object[]{c.getCourseId(), c.getName(), c.getCredit(), c.getScheduleText()});
        };
        reload.run();

        // ช่องกรอกข้อมูลวิชาใหม่ (เวลาเริ่มต้น 09:00-12:00)
        JTextField id = new JTextField(), name = new JTextField(), credit = new JTextField(),
                start = new JTextField("09:00"), end = new JTextField("12:00");
        JComboBox<String> day = new JComboBox<>(new String[]{"TBA", "MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"});

        // ฟอร์ม 2 แถว: แถวบนเป็นป้ายชื่อ แถวล่างเป็นช่องกรอก
        JPanel form = new JPanel(new GridLayout(2, 6, 6, 2));
        form.setOpaque(false);
        form.setBounds(20, 400, 720, 50);
        for (String s : new String[]{"ID", "Name", "Credit", "Day", "Start", "End"}) form.add(new JLabel(s));
        form.add(id); form.add(name); form.add(credit); form.add(day); form.add(start); form.add(end);
        p.add(form);

        // ปุ่ม Add: ส่งข้อมูลให้ Controller ตรวจและบันทึก
        JButton add = button("Add", 230, 470, 130, 35);
        add.addActionListener(e -> {
            try {
                ctrl.addCourse(id.getText(), name.getText(), credit.getText(),
                        (String) day.getSelectedItem(), start.getText(), end.getText());
                id.setText(""); name.setText(""); credit.setText(""); // ล้างช่องกรอก
                reload.run();
            } catch (IllegalArgumentException ex) { err(ex.getMessage()); }
        });
        p.add(add);

        // ปุ่ม Delete: ลบวิชาที่เลือกในตาราง (มีกล่องยืนยันก่อน)
        JButton del = button("Delete", 390, 470, 130, 35);
        del.addActionListener(e -> {
            int row = tbl.getSelectedRow();
            if (row < 0) { err("Please select a course first."); return; }
            String cid = tbl.getValueAt(row, 0).toString();
            if (JOptionPane.showConfirmDialog(this, "Delete " + cid + "?", "Confirm",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                try { ctrl.deleteCourse(cid); reload.run(); }
                catch (IllegalArgumentException ex) { err(ex.getMessage()); }
            }
        });
        p.add(del);
        return p;
    }

    // ---------- Registration Period ----------

    /** หน้ากำหนดช่วงเวลาเปิด-ปิดการลงทะเบียน */
    private JPanel periodPanel() {
        JPanel p = page("Registration Period");

        // แสดงสถานะปัจจุบันของการลงทะเบียน
        JLabel status = new JLabel(new ScheduleService().getRegistrationStatusText());
        status.setFont(new Font("Segoe UI", Font.BOLD, 14));
        status.setBounds(32, 85, 600, 22);
        p.add(status);

        // ใส่ค่าช่วงเวลาปัจจุบันลงในช่องกรอก (ว่างถ้ายังไม่เคยกำหนด)
        RegistrationPeriod cur = new RegistrationPeriodRepository().getPeriod();
        JTextField open = new JTextField(cur == null ? "" : cur.getOpenAt().toString());
        JTextField close = new JTextField(cur == null ? "" : cur.getCloseAt().toString());

        JLabel l1 = new JLabel("Open at (yyyy-MM-ddTHH:mm)");
        l1.setBounds(32, 140, 300, 20);
        open.setBounds(32, 165, 260, 35);
        JLabel l2 = new JLabel("Close at (yyyy-MM-ddTHH:mm)");
        l2.setBounds(32, 220, 300, 20);
        close.setBounds(32, 245, 260, 35);
        p.add(l1); p.add(open); p.add(l2); p.add(close);

        // ปุ่ม Save: ตรวจรูปแบบและบันทึก แล้วโหลดหน้าใหม่เพื่ออัปเดตสถานะ
        JButton save = button("Save", 32, 305, 130, 35);
        save.addActionListener(e -> {
            try {
                ctrl.setPeriod(open.getText(), close.getText());
                JOptionPane.showMessageDialog(this, "Saved.");
                show(periodPanel());
            } catch (IllegalArgumentException ex) { err(ex.getMessage()); }
        });
        p.add(save);
        return p;
    }
}
