package repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * อ่าน/เขียนข้อมูลการลงทะเบียนในไฟล์ CSV
 * CSV format: enrollmentId,studentId,courseId,status,enrolledAt
 * enrolledAt = yyyy-MM-dd HH:mm:ss (แถวเก่าที่ไม่มีคอลัมน์นี้จะแสดงเป็น "-")
 */
public class EnrollmentRepository {

    private static final String FILE_PATH = "data/Enrollment.csv";
    // รูปแบบเวลาที่บันทึกลงไฟล์
    private static final DateTimeFormatter TIME_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /** เพิ่มแถว ENROLLED ต่อท้ายไฟล์ (พร้อมเวลาที่ลงทะเบียน) */
    public boolean enroll(String studentId, String courseId) {
        try (FileWriter fw = new FileWriter(FILE_PATH, true);
             PrintWriter out = new PrintWriter(fw)) {
            // สร้างรหัสการลงทะเบียนแบบสุ่ม เช่น ENRa1b2c3d4
            String enrollId = "ENR" + UUID.randomUUID().toString().substring(0, 8);
            String now = TIME_FMT.format(LocalDateTime.now());
            out.println(enrollId + "," + studentId + "," + courseId + ",ENROLLED," + now);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /** ลบแถวของนักศึกษาคนนี้ในวิชานี้ (ถอนวิชา) */
    public boolean withdraw(String studentId, String courseId) {
        List<String> keep = new ArrayList<>(); // แถวที่ต้องเก็บไว้
        boolean removed = false;

        for (String line : readLines()) {
            String[] data = line.split(",", -1);
            // data[1]=studentId, data[2]=courseId
            if (data.length >= 4 && data[1].trim().equals(studentId) && data[2].trim().equals(courseId)) {
                removed = true; // ไม่เก็บแถวนี้ = ลบ
            } else {
                keep.add(line);
            }
        }

        // เขียนไฟล์ใหม่เฉพาะเมื่อมีการลบจริง
        if (removed) {
            writeLines(keep);
        }
        return removed;
    }

    /** ลบแถวของวิชานี้ทั้งหมด ทุกคน (ใช้ตอน admin ลบวิชา) */
    public void removeByCourse(String courseId) {
        List<String> keep = new ArrayList<>();
        boolean removed = false;

        for (String line : readLines()) {
            String[] data = line.split(",", -1);
            if (data.length >= 4 && data[2].trim().equals(courseId)) {
                removed = true;
            } else {
                keep.add(line);
            }
        }

        if (removed) {
            writeLines(keep);
        }
    }

    /** รหัสวิชาที่นักศึกษาคนนี้ลงทะเบียนอยู่ (status ENROLLED) */
    public List<String> getEnrolledCourseIds(String studentId) {
        List<String> ids = new ArrayList<>();
        List<String> lines = readLines();

        for (int i = 1; i < lines.size(); i++) { // i = 1 เพื่อข้ามบรรทัด header
            String[] data = lines.get(i).split(",", -1);
            if (data.length >= 4 && data[1].trim().equals(studentId) && data[3].trim().equals("ENROLLED")) {
                ids.add(data[2].trim());
            }
        }
        return ids;
    }

    /** เวลาที่นักศึกษาลงทะเบียนวิชานี้ (ถ้าไม่มีข้อมูลจะได้ "-") */
    public String getEnrolledAt(String studentId, String courseId) {
        List<String> lines = readLines();

        for (int i = 1; i < lines.size(); i++) {
            String[] data = lines.get(i).split(",", -1);
            if (data.length >= 4 && data[1].trim().equals(studentId) && data[2].trim().equals(courseId)
                    && data[3].trim().equals("ENROLLED")) {
                // data[4] = enrolledAt (แถวเก่าอาจไม่มีคอลัมน์นี้)
                if (data.length >= 5 && !data[4].trim().isEmpty()) {
                    return data[4].trim();
                }
                return "-";
            }
        }
        return "-";
    }

    // ---------- อ่าน/เขียนไฟล์ทั้งไฟล์ ----------

    /** อ่านทุกบรรทัด (รวม header) ถ้าไฟล์ไม่มีจะได้ list ว่าง */
    private List<String> readLines() {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lines;
    }

    /** เขียนทับทั้งไฟล์ */
    private void writeLines(List<String> lines) {
        try (PrintWriter out = new PrintWriter(new FileWriter(FILE_PATH, false))) {
            for (String line : lines) {
                out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
