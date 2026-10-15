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
 * อ่าน/เขียนข้อมูลรายวิชาในไฟล์ CSV
 * CSV format: courseId,name,credit,day,startTime,endTime
 *   day       = MON,TUE,WED,THU,FRI,SAT,SUN
 *   startTime = HH:mm (เช่น 09:00)   endTime = HH:mm
 * ถ้าคอลัมน์เวลาว่าง/ไม่มี จะถือว่า "ยังไม่กำหนดเวลาเรียน" (TBA)
 */
public class CourseRepository {

    private static final String FILE_PATH = "data/Course.csv";

    /** อ่านรายวิชาทั้งหมดจากไฟล์ (แถวที่ข้อมูลผิดรูปแบบจะถูกข้าม) */
    public List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            br.readLine(); // skip header
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] data = line.split(",", -1);
                // ต้องมีอย่างน้อย 3 คอลัมน์ (id, name, credit) และ id ไม่ว่าง
                if (data.length >= 3 && !data[0].trim().isEmpty()) {
                    try {
                        int credit = Integer.parseInt(data[2].trim());
                        DayOfWeek day = null;
                        LocalTime start = null, end = null;
                        // คอลัมน์ day/start/end เป็นข้อมูลเสริม (อาจไม่มี)
                        if (data.length >= 6) {
                            try {
                                day = parseDay(data[3]);
                                start = LocalTime.parse(data[4].trim());
                                end = LocalTime.parse(data[5].trim());
                                // ข้อมูลเวลาไม่สมเหตุสมผล -> ถือเป็น TBA
                                if (day == null || !start.isBefore(end)) {
                                    day = null; start = null; end = null;
                                }
                            } catch (Exception ignored) {
                                // แปลงเวลาไม่ได้ -> ถือเป็น TBA
                                day = null; start = null; end = null;
                            }
                        }
                        courses.add(new Course(data[0].trim(), data[1].trim(), credit, day, start, end));
                    } catch (NumberFormatException ignored) {
                        // skip malformed row (หน่วยกิตไม่ใช่ตัวเลข)
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return courses;
    }

    /** ค้นหารายวิชาจากรหัสวิชา (คืน null ถ้าไม่พบ) */
    public Course getCourseById(String courseId) {
        for (Course c : getAllCourses()) {
            if (c.getCourseId().equals(courseId)) {
                return c;
            }
        }
        return null;
    }

    /** บันทึกรายวิชาทั้งหมดลงไฟล์ (เขียนทับของเดิมทั้งไฟล์ พร้อมบรรทัด header) */
    public void saveAll(List<Course> list) {
    try (PrintWriter out = new PrintWriter(new FileWriter(FILE_PATH, false))) {
        out.println("courseId,name,credit,day,startTime,endTime");
        for (Course c : list) {
            // ถ้ามีเวลาเรียนให้เขียน day,start,end  ถ้าไม่มีเขียนเป็นคอลัมน์ว่าง ",,"
            String t = c.hasSchedule()
                    ? c.getDay().name().substring(0, 3) + "," + c.getStartTime() + "," + c.getEndTime()
                    : ",,";
            out.println(c.getCourseId() + "," + c.getName() + "," + c.getCredit() + "," + t);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}
    /** แปลงข้อความ เช่น "MON" เป็น DayOfWeek (คืน null ถ้าสั้นเกินไปหรือไม่ตรงกับวันใดเลย) */
    private DayOfWeek parseDay(String s) {
        String t = s.trim().toUpperCase();
        if (t.length() < 3) return null;
        for (DayOfWeek d : DayOfWeek.values()) {
            if (d.name().startsWith(t)) return d;
        }
        return null;
    }
}
