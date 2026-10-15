package repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Repository สำหรับข้อมูลการลงทะเบียนเรียน (อ่าน/เขียนไฟล์ CSV)
 * CSV format: enrollmentId,studentId,courseId,status,enrolledAt
 * enrolledAt = yyyy-MM-dd HH:mm:ss (แถวเก่าที่ไม่มีคอลัมน์นี้จะแสดงเป็น "-")
 */
public class EnrollmentRepository {

    private static final String FILE_PATH = "data/Enrollment.csv";
    public static final DateTimeFormatter TIME_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /** Appends an ENROLLED row (พร้อมเวลาที่ลงทะเบียน) for this student/course. */
    public boolean enroll(String studentId, String courseId) {
        // เปิดไฟล์โหมด append เพื่อเพิ่มแถวใหม่ต่อท้าย
        try (FileWriter fw = new FileWriter(FILE_PATH, true);
             PrintWriter out = new PrintWriter(fw)) {
            String enrollId = "ENR" + System.currentTimeMillis(); // สร้าง id จากเวลาปัจจุบัน (มิลลิวินาที)
            String now = TIME_FMT.format(LocalDateTime.now());
            out.println(enrollId + "," + studentId + "," + courseId + ",ENROLLED," + now);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /** Removes the enrollment row matching this student/course. */
    public boolean withdraw(String studentId, String courseId) {
        List<String> keep = new ArrayList<>(); // เก็บบรรทัดที่ต้องการเก็บไว้
        boolean removed = false;
        File file = new File(FILE_PATH);
        if (!file.exists()) return false;

        // ขั้นที่ 1: อ่านทุกบรรทัด แล้วคัดแถวที่ตรงกับนักศึกษา/วิชานี้ออก
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String header = br.readLine();
            if (header != null) keep.add(header);
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) { keep.add(line); continue; }
                String[] data = line.split(",", -1);
                // data[1]=studentId, data[2]=courseId
                if (data.length >= 4 && data[1].trim().equals(studentId) && data[2].trim().equals(courseId)) {
                    removed = true; // ไม่ใส่ลง keep -> เท่ากับลบแถวนี้
                } else {
                    keep.add(line);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        // ขั้นที่ 2: ถ้ามีการลบ ให้เขียนไฟล์ใหม่ทับของเดิม
        if (removed) {
            try (PrintWriter out = new PrintWriter(new FileWriter(file, false))) {
                for (String l : keep) out.println(l);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return removed;
    }

    /** Course IDs this student currently has status ENROLLED for. */
    public List<String> getEnrolledCourseIds(String studentId) {
        return new ArrayList<>(getEnrolledAtMap(studentId).keySet());
    }

    /** courseId -> เวลาที่ลงทะเบียน (หรือ "-" ถ้าไม่มีข้อมูล) ของวิชาที่ ENROLLED */
    public Map<String, String> getEnrolledAtMap(String studentId) {
        Map<String, String> map = new LinkedHashMap<>(); // LinkedHashMap เพื่อคงลำดับตามไฟล์
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            br.readLine(); // ข้ามบรรทัด header
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] data = line.split(",", -1);
                // data[3]=status, data[4]=enrolledAt (อาจไม่มีในแถวเก่า)
                if (data.length >= 4 && data[1].trim().equals(studentId) && data[3].trim().equals("ENROLLED")) {
                    String at = (data.length >= 5 && !data[4].trim().isEmpty()) ? data[4].trim() : "-";
                    map.put(data[2].trim(), at);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
