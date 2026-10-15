package controller;

import model.Course;
import repository.CourseRepository;
import repository.EnrollmentRepository;
import service.ScheduleService;

/**
 * logic ของการลงทะเบียน/ถอนรายวิชา (ห้าม import javax.swing)
 * UI เอา e.getMessage() ไปแสดงได้เลย
 */
public class EnrollmentController {

    private final CourseRepository courseRepository = new CourseRepository();
    private final EnrollmentRepository enrollmentRepository = new EnrollmentRepository();
    // ใช้ตรวจช่วงเวลาลงทะเบียน และตรวจเวลาเรียนชนกัน
    private final ScheduleService scheduleService = new ScheduleService();

    /**
     * ลงทะเบียนวิชา โดยตรวจตามลำดับ: ช่วงเวลา -> วิชามีอยู่จริง -> ลงซ้ำ -> เวลาชน -> บันทึก
     * @throws IllegalArgumentException ถ้านอกช่วงเวลา / วิชาไม่มี / ลงซ้ำ / เวลาเรียนชน
     */
    public void register(String studentId, String courseId) {
        // 1) ต้องอยู่ในช่วงเวลาที่เปิดให้ลงทะเบียน
        if (!scheduleService.isRegistrationOpen()) {
            throw new IllegalArgumentException(scheduleService.getRegistrationStatusText());
        }
        // 2) วิชาต้องมีอยู่ในระบบ
        Course course = courseRepository.getCourseById(courseId);
        if (course == null) {
            throw new IllegalArgumentException("Course not found: " + courseId);
        }
        // 3) ห้ามลงทะเบียนวิชาเดิมซ้ำ
        if (enrollmentRepository.getEnrolledCourseIds(studentId).contains(courseId)) {
            throw new IllegalArgumentException("You are already registered for " + courseId + ".");
        }
        // 4) ห้ามมีเวลาเรียนชนกับวิชาที่ลงไว้แล้ว
        Course conflict = scheduleService.findConflict(studentId, course);
        if (conflict != null) {
            throw new IllegalArgumentException("Time conflict!\n"
                    + course.getCourseId() + " (" + course.getScheduleText() + ")\n"
                    + "overlaps with\n"
                    + conflict.getCourseId() + " (" + conflict.getScheduleText() + ")");
        }
        // 5) ผ่านทุกเงื่อนไข -> บันทึกลงไฟล์
        if (!enrollmentRepository.enroll(studentId, courseId)) {
            throw new IllegalArgumentException("Could not register this course.");
        }
    }

    /**
     * ถอนรายวิชา (ต้องอยู่ในช่วงเวลาลงทะเบียนเช่นกัน)
     * @throws IllegalArgumentException ถ้านอกช่วงเวลา หรือถอนไม่สำเร็จ
     */
    public void drop(String studentId, String courseId) {
        if (!scheduleService.isRegistrationOpen()) {
            throw new IllegalArgumentException(scheduleService.getRegistrationStatusText());
        }
        // withdraw คืน false ถ้าไม่พบแถวที่ต้องลบ
        if (!enrollmentRepository.withdraw(studentId, courseId)) {
            throw new IllegalArgumentException("Could not withdraw this course.");
        }
    }
}
