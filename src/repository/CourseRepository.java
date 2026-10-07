package repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.io.PrintWriter;

import model.Course;


/**
 * CSV format: courseId,name,credit,day,startTime,endTime
 *   day       = MON,TUE,WED,THU,FRI,SAT,SUN
 *   startTime = HH:mm (เช่น 09:00)   endTime = HH:mm
 * ถ้าคอลัมน์เวลาว่าง/ไม่มี จะถือว่า "ยังไม่กำหนดเวลาเรียน" (TBA)
 */
public class CourseRepository {

    private static final String FILE_PATH = "data/Course.csv";

    public List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            br.readLine(); // skip header
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] data = line.split(",", -1);
                if (data.length >= 3 && !data[0].trim().isEmpty()) {
                    try {
                        int credit = Integer.parseInt(data[2].trim());
                        DayOfWeek day = null;
                        LocalTime start = null, end = null;
                        if (data.length >= 6) {
                            try {
                                day = parseDay(data[3]);
                                start = LocalTime.parse(data[4].trim());
                                end = LocalTime.parse(data[5].trim());
                                if (day == null || !start.isBefore(end)) {
                                    day = null; start = null; end = null;
                                }
                            } catch (Exception ignored) {
                                day = null; start = null; end = null;
                            }
                        }
                        courses.add(new Course(data[0].trim(), data[1].trim(), credit, day, start, end));
                    } catch (NumberFormatException ignored) {
                        // skip malformed row
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return courses;
    }

    public Course getCourseById(String courseId) {
        for (Course c : getAllCourses()) {
            if (c.getCourseId().equals(courseId)) {
                return c;
            }
        }
        return null;
    }

    public void saveAll(List<Course> list) {
    try (PrintWriter out = new PrintWriter(new FileWriter(FILE_PATH, false))) {
        out.println("courseId,name,credit,day,startTime,endTime");
        for (Course c : list) {
            String t = c.hasSchedule()
                    ? c.getDay().name().substring(0, 3) + "," + c.getStartTime() + "," + c.getEndTime()
                    : ",,";
            out.println(c.getCourseId() + "," + c.getName() + "," + c.getCredit() + "," + t);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}
    private DayOfWeek parseDay(String s) {
        String t = s.trim().toUpperCase();
        if (t.length() < 3) return null;
        for (DayOfWeek d : DayOfWeek.values()) {
            if (d.name().startsWith(t)) return d;
        }
        return null;
    }
}
