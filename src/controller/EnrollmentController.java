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
    private final ScheduleService scheduleService = new ScheduleService();

    /** @throws IllegalArgumentException ถ้านอกช่วงเวลา / วิชาไม่มี / ลงซ้ำ / เวลาเรียนชน */
    public void register(String studentId, String courseId) {
        if (!scheduleService.isRegistrationOpen()) {
            throw new IllegalArgumentException(scheduleService.getRegistrationStatusText());
        }
        Course course = courseRepository.getCourseById(courseId);
        if (course == null) {
            throw new IllegalArgumentException("Course not found: " + courseId);
        }
        if (enrollmentRepository.getEnrolledCourseIds(studentId).contains(courseId)) {
            throw new IllegalArgumentException("You are already registered for " + courseId + ".");
        }
        Course conflict = scheduleService.findConflict(studentId, course);
        if (conflict != null) {
            throw new IllegalArgumentException("Time conflict!\n"
                    + course.getCourseId() + " (" + course.getScheduleText() + ")\n"
                    + "overlaps with\n"
                    + conflict.getCourseId() + " (" + conflict.getScheduleText() + ")");
        }
        if (!enrollmentRepository.enroll(studentId, courseId)) {
            throw new IllegalArgumentException("Could not register this course.");
        }
    }

    /** @throws IllegalArgumentException ถ้านอกช่วงเวลา หรือถอนไม่สำเร็จ */
    public void drop(String studentId, String courseId) {
        if (!scheduleService.isRegistrationOpen()) {
            throw new IllegalArgumentException(scheduleService.getRegistrationStatusText());
        }
        if (!enrollmentRepository.withdraw(studentId, courseId)) {
            throw new IllegalArgumentException("Could not withdraw this course.");
        }
    }
}
