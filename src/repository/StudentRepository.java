package repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

import model.Student;

/**
 * อ่าน/เขียนข้อมูลนักศึกษาในไฟล์ CSV
 * CSV format: id,name,email,password
 */
public class StudentRepository {

    private static final String FILE_PATH = "data/Student.csv";

    /** Returns the Student if id/password match, otherwise null. */
    public Student login(String studentId, String password) {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            br.readLine(); // skip header
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] data = line.split(",", -1);
                // data[0]=id, data[1]=name, data[2]=email, data[3]=password
                if (data.length >= 4 && data[0].trim().equals(studentId) && data[3].trim().equals(password)) {
                    return new Student(data[0].trim(), data[1].trim(), data[2].trim());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /** True if a student with this id already exists. */
    public boolean exists(String studentId) {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            br.readLine(); // skip header
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] data = line.split(",", -1);
                if (data.length >= 1 && data[0].trim().equals(studentId)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /** Appends a new student row. Returns false on error or duplicate id. */
    public boolean register(String studentId, String name, String email, String password) {
        // กัน ID ซ้ำอีกชั้นหนึ่ง
        if (exists(studentId)) {
            return false;
        }
        // เปิดไฟล์โหมดต่อท้าย (append = true) เพื่อไม่ให้ข้อมูลเดิมหาย
        try (FileWriter fw = new FileWriter(FILE_PATH, true);
             PrintWriter out = new PrintWriter(fw)) {
            out.println(studentId + "," + name + "," + email + "," + password);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
