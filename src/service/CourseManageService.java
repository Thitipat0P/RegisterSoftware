package service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CourseManageService {
    
    private static final String FILE_PATH = "C:\\Users\\wutth\\Downloads\\RegisterSoftware-main\\RegisterSoftware-main\\src\\data\\courses.csv";

    // 1. เพิ่มรายวิชา (มี maxSeats เพิ่มเข้ามา)
    public String addCourse(String courseId, String name, int credits, int maxSeats) {
        if (courseId.isEmpty() || name.isEmpty() || maxSeats <= 0) {
            return "กรุณากรอกข้อมูลให้ครบถ้วนและจำนวนที่นั่งต้องมากกว่า 0";
        }

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length > 0 && data[0].equals(courseId)) {
                    return "รหัสวิชานี้มีในระบบแล้ว!";
                }
            }
        } catch (IOException e) {
            // ไม่มีไฟล์ ไม่เป็นไร
        }

        // บันทึกต่อท้ายไฟล์
        try (FileWriter fw = new FileWriter(FILE_PATH, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            
            out.println(courseId + "," + name + "," + credits + "," + maxSeats);
            return "SUCCESS";
        } catch (IOException e) {
            return "เกิดข้อผิดพลาดในการบันทึกไฟล์: " + e.getMessage();
        }
    }

    // 2. ดึงข้อมูลรายวิชาทั้งหมดเพื่อส่งไปแสดงบนหน้าจอ
    public List<String[]> getAllCourses() {
        List<String[]> courses = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                // ข้ามบรรทัดว่างและบรรทัด Header (courseId,...)
                if (line.trim().isEmpty() || line.startsWith("courseId")) continue;
                courses.add(line.split(","));
            }
        } catch (IOException e) {
            // คืนค่า List เปล่าถ้ายังไม่มีข้อมูล
        }
        return courses;
    }

    // 3. ลบรายวิชา
    public String deleteCourse(String courseId) {
        List<String> remainingLines = new ArrayList<>();
        boolean found = false;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] data = line.split(",");
                
                // ถ้ารหัสตรงกับวิชาที่ต้องการลบ เราจะไม่เอาใส่ remainingLines
                if (data[0].equals(courseId)) {
                    found = true;
                } else {
                    remainingLines.add(line);
                }
            }
        } catch (IOException e) {
            return "เกิดข้อผิดพลาดในการอ่านไฟล์";
        }

        if (!found) return "ไม่พบรหัสวิชานี้ในระบบ";

        // เขียนไฟล์ทับใหม่ทั้งหมดด้วยข้อมูลที่เหลืออยู่
        try (FileWriter fw = new FileWriter(FILE_PATH, false); // false = เขียนทับทั้งหมด
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
             
            for (String l : remainingLines) {
                out.println(l);
            }
            return "SUCCESS";
        } catch (IOException e) {
            return "เกิดข้อผิดพลาดในการลบข้อมูล: " + e.getMessage();
        }
    }
}