package controller;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;

import model.Admin;
import model.Course;
import repository.AdminRepository;
import repository.CourseRepository;
import repository.EnrollmentRepository;
import repository.RegistrationPeriodRepository;

/**
 * Controller ฝั่ง Admin
 * รับผิดชอบ: login แอดมิน, เพิ่ม/ลบรายวิชา, ตั้งช่วงเวลาเปิด-ปิดลงทะเบียน
 * หน้าที่หลักคือ "ตรวจสอบข้อมูล (validate)" ก่อนส่งต่อให้ Repository บันทึกลงไฟล์ CSV
 * ห้าม import javax.swing เพราะ logic ต้องแยกออกจากหน้าจอ
 */
public class AdminController {

    // repository ที่ใช้อ่าน/เขียนไฟล์ CSV แต่ละประเภท
    private final AdminRepository adminRepository = new AdminRepository();
    private final CourseRepository courseRepository = new CourseRepository();
    private final EnrollmentRepository enrollmentRepository = new EnrollmentRepository();
    private final RegistrationPeriodRepository periodRepository = new RegistrationPeriodRepository();

    /**
     * ตรวจสอบการ login ของ admin
     * @return Admin ถ้า login ผ่าน, null ถ้าไม่ใช่ admin
     */
    public Admin login(String id, String password) {
        // ถ้าไม่ได้ส่งค่ามา ถือว่า login ไม่ผ่าน
        if (id == null || password == null) return null;
        // ตัดช่องว่างหน้า-หลังก่อนเทียบกับข้อมูลในไฟล์
        return adminRepository.login(id.trim(), password.trim());
    }

    /**
     * เพิ่มรายวิชาใหม่
     * @param day "TBA" หรือ MON..SUN
     * @throws IllegalArgumentException ถ้าข้อมูลไม่ถูกต้อง (UI นำ message ไปแสดงได้เลย)
     */
    public void addCourse(String id, String name, String credit, String day, String start, String end) {
        // ต้องกรอก ID, ชื่อ และหน่วยกิตให้ครบ
        if (isBlank(id) || isBlank(name) || isBlank(credit)) {
            throw new IllegalArgumentException("Please fill in ID, Name and Credit.");
        }
        // CSV ใช้คอมมาแบ่งคอลัมน์ ถ้าข้อมูลมีคอมมาไฟล์จะพัง
        if (id.contains(",") || name.contains(",")) {
            throw new IllegalArgumentException("ID/Name must not contain commas.");
        }
        // แปลงหน่วยกิตเป็นตัวเลข
        int cr;
        try { cr = Integer.parseInt(credit.trim()); }
        catch (NumberFormatException e) { throw new IllegalArgumentException("Credit must be a number."); }
        if (cr <= 0) throw new IllegalArgumentException("Credit must be greater than 0.");

        // ห้ามใช้รหัสวิชาซ้ำ (ไม่สนตัวพิมพ์เล็ก/ใหญ่)
        List<Course> all = courseRepository.getAllCourses();
        for (Course c : all) {
            if (c.getCourseId().equalsIgnoreCase(id.trim())) {
                throw new IllegalArgumentException("Course ID already exists: " + id.trim());
            }
        }

        // d = วันเรียน, s = เวลาเริ่ม, e = เวลาเลิก (ถ้าเป็น TBA จะเป็น null ทั้งหมด)
        DayOfWeek d = null;
        LocalTime s = null, e = null;
        if (day != null && !day.equals("TBA")) {
            try {
                // หา DayOfWeek ที่ชื่อขึ้นต้นด้วย MON, TUE, ...
                for (DayOfWeek x : DayOfWeek.values()) if (x.name().startsWith(day)) d = x;
                // แปลงข้อความ HH:mm เป็น LocalTime
                s = LocalTime.parse(start.trim());
                e = LocalTime.parse(end.trim());
            } catch (DateTimeParseException ex) {
                throw new IllegalArgumentException("Time must be HH:mm (e.g. 09:00).");
            }
            // เวลาเริ่มต้องมาก่อนเวลาเลิก
            if (d == null || !s.isBefore(e)) {
                throw new IllegalArgumentException("Start time must be before end time.");
            }
        }
        // เพิ่มวิชาใหม่ลงลิสต์ แล้วบันทึกทับไฟล์ทั้งไฟล์
        all.add(new Course(id.trim(), name.trim(), cr, d, s, e));
        courseRepository.saveAll(all);
    }

    /**
     * ลบรายวิชา (และลบข้อมูลการลงทะเบียนของวิชานั้นทั้งหมดด้วย)
     * @throws IllegalArgumentException ถ้าไม่พบวิชา
     */
    public void deleteCourse(String courseId) {
        List<Course> all = courseRepository.getAllCourses();
        // ค้นหาวิชาที่ต้องการลบ
        Course found = null;
        for (Course c : all) {
            if (c.getCourseId().equals(courseId)) {
                found = c;
            }
        }
        if (found == null) {
            throw new IllegalArgumentException("Course not found: " + courseId);
        }
        all.remove(found);
        courseRepository.saveAll(all);
        enrollmentRepository.removeByCourse(courseId); // ลบข้อมูลลงทะเบียนของวิชานี้ด้วย
    }

    /**
     * ตั้งช่วงเวลาเปิด-ปิดลงทะเบียน
     * @param open/close ISO เช่น 2026-10-01T08:00
     */
    public void setPeriod(String open, String close) {
        try {
            LocalDateTime o = LocalDateTime.parse(open.trim());
            LocalDateTime c = LocalDateTime.parse(close.trim());
            // เวลาเปิดต้องมาก่อนเวลาปิด
            if (!o.isBefore(c)) throw new IllegalArgumentException("Open time must be before close time.");
            if (!periodRepository.save(o, c)) throw new IllegalArgumentException("Could not save period.");
        } catch (DateTimeParseException ex) {
            // รูปแบบวันที่/เวลาไม่ถูกต้อง
            throw new IllegalArgumentException("Format must be yyyy-MM-ddTHH:mm (e.g. 2026-10-01T08:00).");
        }
    }

    /** ตรวจว่าข้อความเป็น null หรือว่างเปล่า (รวมถึงมีแต่ช่องว่าง) */
    private boolean isBlank(String s) { return s == null || s.trim().isEmpty(); }
}
