package service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import model.Course;
import model.RegistrationPeriod;
import repository.CourseRepository;
import repository.EnrollmentRepository;
import repository.RegistrationPeriodRepository;

/** ระบบเวลา: ตรวจช่วงเวลาลงทะเบียน และตรวจเวลาเรียนชนกัน */
public class ScheduleService {

    private final CourseRepository courseRepository = new CourseRepository();
    private final EnrollmentRepository enrollmentRepository = new EnrollmentRepository();
    private final RegistrationPeriodRepository periodRepository = new RegistrationPeriodRepository();

    // ---------- ช่วงเวลาลงทะเบียน ----------

    /** ไม่มีไฟล์กำหนดช่วงเวลา = เปิดตลอด */
    public boolean isRegistrationOpen() {
        RegistrationPeriod p = periodRepository.getPeriod();
        return p == null || p.isOpen(LocalDateTime.now());
    }

    /** ข้อความสถานะการลงทะเบียน สำหรับแสดงบนหน้าจอ/แจ้งผู้ใช้ */
    public String getRegistrationStatusText() {
        RegistrationPeriod p = periodRepository.getPeriod();
        if (p == null) return "Registration is open";
        return p.getStatusText(LocalDateTime.now());
    }

    // ---------- เวลาเรียนชนกัน ----------

    /** วิชาที่นักศึกษาลงทะเบียนอยู่ในขณะนี้ (แปลงรหัสวิชาเป็นอ็อบเจกต์ Course) */
    public List<Course> getEnrolledCourses(String studentId) {
        List<String> ids = enrollmentRepository.getEnrolledCourseIds(studentId);
        List<Course> result = new ArrayList<>();
        for (Course c : courseRepository.getAllCourses()) {
            if (ids.contains(c.getCourseId())) result.add(c);
        }
        return result;
    }

    /** คืนวิชาที่เวลาชนกับ target (หรือ null ถ้าไม่ชน) */
    public Course findConflict(List<Course> enrolled, Course target) {
        for (Course c : enrolled) {
            // ไม่เทียบกับตัวเอง และเช็คว่าเวลาซ้อนทับกันหรือไม่
            if (!c.getCourseId().equals(target.getCourseId()) && c.conflictsWith(target)) {
                return c;
            }
        }
        return null;
    }

    /** เหมือนข้างบน แต่รับรหัสนักศึกษา แล้วดึงวิชาที่ลงไว้ให้เอง */
    public Course findConflict(String studentId, Course target) {
        return findConflict(getEnrolledCourses(studentId), target);
    }
}
