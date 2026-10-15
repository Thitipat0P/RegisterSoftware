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
 * Repository สำหรับข้อมูลรายวิชา (อ่าน/เขียนไฟล์ CSV)
 * CSV format: courseId,name,credit,day,startTime,endTime
 *   day       = MON,TUE,WED,THU,FRI,SAT,SUN
 *   startTime = HH:mm (เช่น 09:00)   endTime = HH:mm
 * ถ้าคอลัมน์เวลาว่าง/ไม่มี จะถือว่า "ยังไม่กำหนดเวลาเรียน" (TBA)
 */
public class CourseRepository {

    private static final String FILE_PATH = "data/Course.csv";

    /** อ่านวิชาทั้งหมดจากไฟล์ CSV (แถวที่ข้อมูลผิดรูปแบบจะถูกข้าม) */
    public List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            br.readLine(); // ข้ามบรรทัด header
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] data = line.split(",", -1);
                // ต้องมีอย่างน้อย id, name, credit และ id ต้องไม่ว่าง
                if (data.length >= 3 && !data[0].trim().isEmpty()) {
                    try {
                        int credit = Integer.parseInt(data[2].trim());
                        DayOfWeek day = null;
                        LocalTime start = null, end = null;
                        // มีคอลัมน์เวลาครบ 6 คอลัมน์ จึงลองอ่านเวลาเรียน
                        if (data.length >= 6) {
                            try {
                                day = parseDay(data[3]);
                                start = LocalTime.parse(data[4].trim());
                                end = LocalTime.parse(data[5].trim());
                                // ถ้าวันไม่ถูกต้อง หรือเวลาเริ่มไม่ก่อนเวลาเลิก ให้ถือว่า TBA
                                if (day == null || !start.isBefore(end)) {
                                    day = null; start = null; end = null;
                                }
                            } catch (Exception ignored) {
                                // รูปแบบเวลาผิด -> TBA
                                day = null; start = null; end = null;
                            }
                        }
                        courses.add(new Course(data[0].trim(), data[1].trim(), credit, day, start, end));
                    } catch (NumberFormatException ignored) {
                        // credit ไม่ใช่ตัวเลข -> ข้ามแถวนี้
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return courses;
    }

    /** ค้นหาวิชาจากรหัสวิชา (คืน null ถ้าไม่พบ) */
    public Course getCourseById(String courseId) {
        for (Course c : getAllCourses()) {
            if (c.getCourseId().equals(courseId)) {
                return c;
            }
        }
        return null;
    }

    /** เขียนรายวิชาทั้งหมดทับไฟล์เดิม (ใช้ตอน Admin เพิ่ม/ลบวิชา) */
    public void saveAll(List<Course> list) {
        try (PrintWriter out = new PrintWriter(new FileWriter(FILE_PATH, false))) {
            out.println("courseId,name,credit,day,startTime,endTime");
            for (Course c : list) {
                // วันเก็บเป็นอักษรย่อ 3 ตัว เช่น MON; ถ้าไม่มีเวลาเรียนเขียนคอลัมน์ว่าง 3 ช่อง
                String t = c.hasSchedule()
                        ? c.getDay().name().substring(0, 3) + "," + c.getStartTime() + "," + c.getEndTime()
                        : ",,";
                out.println(c.getCourseId() + "," + c.getName() + "," + c.getCredit() + "," + t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** แปลงข้อความ เช่น "MON" เป็น DayOfWeek (คืน null ถ้าไม่ถูกต้อง) */
    private DayOfWeek parseDay(String s) {
        String t = s.trim().toUpperCase();
        if (t.length() < 3) return null;
        for (DayOfWeek d : DayOfWeek.values()) {
            if (d.name().startsWith(t)) return d;
        }
        return null;
    }
}
