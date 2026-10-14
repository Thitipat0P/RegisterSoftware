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
 * หน้าแสดง Dashboard ของนักศึกษา
 * แสดงวิชาที่ลงทะเบียน เวลาเรียน เวลาที่ลงทะเบียน
 */
public class Dashboard extends JPanel {
 
    private final Student student;
 
    // ตัวดึงข้อมูลวิชาทั้งหมด และข้อมูลการลงทะเบียนจากที่เก็บข้อมูล
    private final CourseRepository courseRepository = new CourseRepository();
    private final EnrollmentRepository enrollmentRepository = new EnrollmentRepository();
 
    private JTable tblCourses; // ตารางแสดงวิชาที่ลงทะเบียน
    private JLabel lblTotalCredits; // ข้อความหน่วยกิตรวม
 
    // ทำงานทันทีตอนสร้างหน้านี้
    public Dashboard(Student student) {
        this.student = student;
        initComponents();
        loadData(); // ดึงข้อมูลมาใส่ตาราง (ต้องทำหลังสร้างตารางเสร็จ ไม่งั้นจะ error)
    }
 
    // สร้างส่วนต่างๆ ที่จะแสดงบนหน้าจอ
    private void initComponents() {
        setBackground(new Color(236, 238, 248));
        setLayout(null); 
        setPreferredSize(new Dimension(760, 600));
 
        // หัวข้อ Student Dashboard
        JLabel lblTitle = new JLabel("Student Dashboard");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lblTitle.setForeground(new Color(37, 51, 91));
        lblTitle.setBounds(30, 30, 440, 40);
        add(lblTitle);
 
        // Welcome + แสดงชื่อนักศึกษา
        JLabel lblWelcome = new JLabel("Welcome, " + student.getName());
        lblWelcome.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        lblWelcome.setForeground(new Color(86, 101, 144));
        lblWelcome.setBounds(30, 70, 400, 30);
        add(lblWelcome);
 
        // กล่องสีขาว
        JPanel tableContainerPanel = new JPanel();
        tableContainerPanel.setBackground(Color.WHITE);
        tableContainerPanel.setLayout(null);
        tableContainerPanel.setBounds(20, 110, 710, 450);
 
        JLabel lblTableTitle = new JLabel("My Registered Courses");
        lblTableTitle.setFont(new Font("Leelawadee UI", Font.PLAIN, 20));
        lblTableTitle.setForeground(new Color(37, 51, 91));
        lblTableTitle.setBounds(20, 10, 300, 30);
        tableContainerPanel.add(lblTableTitle);
 
        String[] columns = {"Course ID", "Course Name", "Credit", "Class Time", "Registered At"};
 
        // ที่เก็บข้อมูลของตาราง เริ่มต้นยังไม่มีแถวเลยให้เป็น 0 ก่อน
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            // ห้ามแก้ข้อมูลในตาราง
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tblCourses = new JTable(model);
        tblCourses.setRowHeight(40); // ความสูงของแต่ละแถว
 
        // ปรับความกว้างแต่ละคอลัมน์ เลข 0-4 คือลำดับคอลัมน์ นับจากซ้าย เริ่มที่ 0
        tblCourses.getColumnModel().getColumn(0).setPreferredWidth(70); // Course ID
        tblCourses.getColumnModel().getColumn(1).setPreferredWidth(190); // Course Name
        tblCourses.getColumnModel().getColumn(2).setPreferredWidth(50); // Credit
        tblCourses.getColumnModel().getColumn(3).setPreferredWidth(120); // Class Time
        tblCourses.getColumnModel().getColumn(4).setPreferredWidth(140); // Registered At
 
        // ครอบตารางด้วย JScrollPane เพื่อให้เลื่อนได้เมื่อมีวิชาเยอะ และให้หัวตารางแสดงขึ้นมา
        JScrollPane scrollPane = new JScrollPane(tblCourses);
        scrollPane.setBounds(20, 50, 670, 340);
        tableContainerPanel.add(scrollPane);
 
        // ข้อความหน่วยกิตรวม ค่าจริงจะถูกอัปเดตใน loadData
        lblTotalCredits = new JLabel("Total Credits: 0");
        lblTotalCredits.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTotalCredits.setForeground(new Color(37, 51, 91));
        lblTotalCredits.setBounds(520, 400, 170, 30);
        tableContainerPanel.add(lblTotalCredits);
 
        add(tableContainerPanel);
    }
 
    // ดึงข้อมูลวิชาที่นักศึกษาลงทะเบียนไว้ มาแสดงในตาราง และคำนวณหน่วยกิตรวม
    private void loadData() {
        DefaultTableModel model = (DefaultTableModel) tblCourses.getModel();
        model.setRowCount(0); // ล้างแถวเก่าทิ้งก่อน กันข้อมูลซ้ำเวลาโหลดใหม่
 
        // ขอรหัสวิชาที่นักศึกษาคนนี้ลงทะเบียนไว้ 
        List<String> enrolledIds = enrollmentRepository.getEnrolledCourseIds(student.getId());
        int totalCredits = 0; // ตัวนับหน่วยกิตรวม เริ่มที่ 0
 
        // วนดูวิชาทั้งหมดในระบบทีละวิชา
        for (Course course : courseRepository.getAllCourses()) {
            // ถ้ารหัสวิชานี้อยู่ในรายการที่นักศึกษาลงทะเบียนไว้ แปลว่าเป็นวิชาของเขา
            if (enrolledIds.contains(course.getCourseId())) {
                // ไปหาว่านักศึกษาลงทะเบียนวิชานี้ตอนไหน
                String registeredAt = enrollmentRepository.getEnrolledAt(student.getId(), course.getCourseId());
                // เพิ่มเป็นแถวใหม่ในตาราง (เรียงข้อมูลให้ตรงกับหัวคอลัมน์ด้านบน)
                model.addRow(new Object[]{
                        course.getCourseId(), course.getName(), course.getCredit(),
                        course.getScheduleText(), registeredAt});
                totalCredits += course.getCredit(); // บวกหน่วยกิตของวิชานี้เข้าไปในยอดรวม
            }
        }
 
        // อัปเดตข้อความหน่วยกิตรวมให้เป็นค่าที่คำนวณได้
        lblTotalCredits.setText("Total Credits: " + totalCredits);
    }
}
