package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import model.Student;

public class StudentDashboard extends JPanel {

    private final Student student;

    private JPanel sidePanel;
    private JPanel mainContentPanel;

    private JButton btnDashboard;
    private JButton btnRegisterCourse;
    private JButton btnDropCourse;
    private JButton btnLogout;

    public StudentDashboard(Student student) {
        this.student = student;
        initComponents();
    }

    private void initComponents() {
        setLayout(null);
        setPreferredSize(new Dimension(970, 600));

        // ------------------ Side Panel ------------------
        sidePanel = new JPanel();
        sidePanel.setBackground(new Color(37, 51, 91));
        sidePanel.setLayout(null);
        sidePanel.setBounds(0, 0, 210, 600);

        JLabel lblAppTitle1 = new JLabel("Course");
        lblAppTitle1.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblAppTitle1.setForeground(Color.WHITE);
        lblAppTitle1.setBounds(20, 30, 120, 30);
        sidePanel.add(lblAppTitle1);

        JLabel lblAppTitle2 = new JLabel("Registration");
        lblAppTitle2.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblAppTitle2.setForeground(Color.WHITE);
        lblAppTitle2.setBounds(20, 60, 160, 30);
        sidePanel.add(lblAppTitle2);

        btnDashboard = createMenuButton("Dashboard", 130);
        btnDashboard.addActionListener(this::btnDashboardActionPerformed);
        sidePanel.add(btnDashboard);

        btnRegisterCourse = createMenuButton("Register Course", 190);
        btnRegisterCourse.addActionListener(this::btnRegisterCourseActionPerformed);
        sidePanel.add(btnRegisterCourse);

        btnDropCourse = createMenuButton("Drop course", 250);
        btnDropCourse.addActionListener(this::btnDropCourseActionPerformed);
        sidePanel.add(btnDropCourse);

        btnLogout = createMenuButton("Logout", 310);
        btnLogout.addActionListener(this::btnLogoutActionPerformed);
        sidePanel.add(btnLogout);

        add(sidePanel);

        // ------------------ Main Content Panel ------------------
        mainContentPanel = new JPanel();
        mainContentPanel.setBackground(new Color(236, 238, 248));
        mainContentPanel.setLayout(new BorderLayout());
        mainContentPanel.setBounds(210, 0, 760, 600);
        mainContentPanel.add(new Dashboard(student), BorderLayout.CENTER);

        add(mainContentPanel);
    }

    private JButton createMenuButton(String text, int yPosition) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setBackground(new Color(83, 96, 134));
        btn.setForeground(Color.WHITE);
        btn.setBounds(20, yPosition, 170, 40);
        btn.setFocusPainted(false);
        return btn;
    }

    private void showSubPanel(JPanel panel) {
        mainContentPanel.removeAll();
        mainContentPanel.add(panel, BorderLayout.CENTER);
        mainContentPanel.revalidate();
        mainContentPanel.repaint();
    }

    // ===================== Event Handlers =====================

    private void btnDashboardActionPerformed(ActionEvent evt) {
        showSubPanel(new Dashboard(student));
    }

    private void btnRegisterCourseActionPerformed(ActionEvent evt) {
        showRegisterCoursePanel();
    }

    private void btnDropCourseActionPerformed(ActionEvent evt) {
        showDropCoursePanel();
    }

    private void showRegisterCoursePanel() {
        showSubPanel(new RegisterCourse(student, this::showRegisterCoursePanel));
    }

    private void showDropCoursePanel() {
        showSubPanel(new DropCourse(student, this::showDropCoursePanel));
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
}
