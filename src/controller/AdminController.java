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
import repository.RegistrationPeriodRepository;

/** logic ฝั่ง Admin (ห้าม import javax.swing) */
public class AdminController {

    private final AdminRepository adminRepository = new AdminRepository();
    private final CourseRepository courseRepository = new CourseRepository();
    private final RegistrationPeriodRepository periodRepository = new RegistrationPeriodRepository();

    /** @return Admin ถ้า login ผ่าน, null ถ้าไม่ใช่ admin */
    public Admin login(String id, String password) {
        if (id == null || password == null) return null;
        return adminRepository.login(id.trim(), password.trim());
    }

    /** @param day "TBA" หรือ MON..SUN */
    public void addCourse(String id, String name, String credit, String day, String start, String end) {
        if (isBlank(id) || isBlank(name) || isBlank(credit)) {
            throw new IllegalArgumentException("Please fill in ID, Name and Credit.");
        }
        if (id.contains(",") || name.contains(",")) {
            throw new IllegalArgumentException("ID/Name must not contain commas.");
        }
        int cr;
        try { cr = Integer.parseInt(credit.trim()); }
        catch (NumberFormatException e) { throw new IllegalArgumentException("Credit must be a number."); }
        if (cr <= 0) throw new IllegalArgumentException("Credit must be greater than 0.");

        List<Course> all = courseRepository.getAllCourses();
        for (Course c : all) {
            if (c.getCourseId().equalsIgnoreCase(id.trim())) {
                throw new IllegalArgumentException("Course ID already exists: " + id.trim());
            }
        }

        DayOfWeek d = null;
        LocalTime s = null, e = null;
        if (day != null && !day.equals("TBA")) {
            try {
                for (DayOfWeek x : DayOfWeek.values()) if (x.name().startsWith(day)) d = x;
                s = LocalTime.parse(start.trim());
                e = LocalTime.parse(end.trim());
            } catch (DateTimeParseException ex) {
                throw new IllegalArgumentException("Time must be HH:mm (e.g. 09:00).");
            }
            if (d == null || !s.isBefore(e)) {
                throw new IllegalArgumentException("Start time must be before end time.");
            }
        }
        all.add(new Course(id.trim(), name.trim(), cr, d, s, e));
        courseRepository.saveAll(all);  
    }

    public void deleteCourse(String courseId) {
        List<Course> all = courseRepository.getAllCourses();
        if (!all.removeIf(c -> c.getCourseId().equals(courseId))) {
            throw new IllegalArgumentException("Course not found: " + courseId);
        }
        courseRepository.saveAll(all);
    }

    /** @param open/close ISO เช่น 2026-10-01T08:00 */
    public void setPeriod(String open, String close) {
        try {
            LocalDateTime o = LocalDateTime.parse(open.trim());
            LocalDateTime c = LocalDateTime.parse(close.trim());
            if (!o.isBefore(c)) throw new IllegalArgumentException("Open time must be before close time.");
            if (!periodRepository.save(o, c)) throw new IllegalArgumentException("Could not save period.");
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException("Format must be yyyy-MM-ddTHH:mm (e.g. 2026-10-01T08:00).");
        }
    }

    private boolean isBlank(String s) { return s == null || s.trim().isEmpty(); }
}
