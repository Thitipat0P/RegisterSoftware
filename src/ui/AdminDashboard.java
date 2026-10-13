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

public class AdminDashboard extends JPanel {

    private static final Color NAVY = new Color(37, 51, 91);
    private static final Color BTN = new Color(83, 96, 134);
    private static final Color BG = new Color(236, 238, 248);

    private final Admin admin;
    private final AdminController ctrl = new AdminController();
    private final JPanel content = new JPanel(new BorderLayout());

    public AdminDashboard(Admin admin) {
        this.admin = admin;
        setLayout(null);
        setPreferredSize(new Dimension(970, 600));

        JPanel side = new JPanel(null);
        side.setBackground(NAVY);
        side.setBounds(0, 0, 210, 600);
        JLabel t = new JLabel("<html>Admin<br>Panel</html>");
        t.setFont(new Font("Segoe UI", Font.BOLD, 24));
        t.setForeground(Color.WHITE);
        t.setBounds(20, 30, 170, 70);
        side.add(t);
        JLabel who = new JLabel(admin.getName());
        who.setForeground(Color.WHITE);
        who.setBounds(20, 100, 170, 20);
        side.add(who);
        side.add(menu("Manage Courses", 130, e -> show(coursePanel())));
        side.add(menu("Registration Period", 190, e -> show(periodPanel())));
        side.add(menu("Logout", 250, e -> logout()));
        add(side);

        content.setBounds(210, 0, 760, 600);
        add(content);
        show(coursePanel());
    }

    // ---------- helpers ----------
    private JButton menu(String text, int y, ActionListener l) {
        JButton b = button(text, 20, y, 170, 40);
        b.addActionListener(l);
        return b;
    }

    private JButton button(String text, int x, int y, int w, int h) {
        JButton b = new JButton(text);
        b.setBackground(BTN);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBounds(x, y, w, h);
        return b;
    }

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

    private void show(JPanel p) {
        content.removeAll();
        content.add(p, BorderLayout.CENTER);
        content.revalidate();
        content.repaint();
    }

    private void err(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

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
    private JPanel coursePanel() {
        JPanel p = page("Manage Courses");
        DefaultTableModel m = new DefaultTableModel(new String[]{"Course ID", "Name", "Credit", "Class Time"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable tbl = new JTable(m);
        tbl.setRowHeight(30);
        JScrollPane sp = new JScrollPane(tbl);
        sp.setBounds(20, 90, 720, 300);
        p.add(sp);

        Runnable reload = () -> {
            m.setRowCount(0);
            for (Course c : new CourseRepository().getAllCourses())
                m.addRow(new Object[]{c.getCourseId(), c.getName(), c.getCredit(), c.getScheduleText()});
        };
        reload.run();

        JTextField id = new JTextField(), name = new JTextField(), credit = new JTextField(),
                start = new JTextField("09:00"), end = new JTextField("12:00");
        JComboBox<String> day = new JComboBox<>(new String[]{"TBA", "MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"});

        JPanel form = new JPanel(new GridLayout(2, 6, 6, 2));
        form.setOpaque(false);
        form.setBounds(20, 400, 720, 50);
        for (String s : new String[]{"ID", "Name", "Credit", "Day", "Start", "End"}) form.add(new JLabel(s));
        form.add(id); form.add(name); form.add(credit); form.add(day); form.add(start); form.add(end);
        p.add(form);

        JButton add = button("Add", 230, 470, 130, 35);
        add.addActionListener(e -> {
            try {
                ctrl.addCourse(id.getText(), name.getText(), credit.getText(),
                        (String) day.getSelectedItem(), start.getText(), end.getText());
                id.setText(""); name.setText(""); credit.setText("");
                reload.run();
            } catch (IllegalArgumentException ex) { err(ex.getMessage()); }
        });
        p.add(add);

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
    private JPanel periodPanel() {
        JPanel p = page("Registration Period");
        JLabel status = new JLabel(new ScheduleService().getRegistrationStatusText());
        status.setFont(new Font("Segoe UI", Font.BOLD, 14));
        status.setBounds(32, 85, 600, 22);
        p.add(status);

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
