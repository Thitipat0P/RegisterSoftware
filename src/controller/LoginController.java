package controller;

import model.Student;
import repository.StudentRepository;

/**
 * "logic ของปุ่ม Login" แยกออกจากหน้าจอ
 * ห้าม import javax.swing ในคลาสนี้
 */
public class LoginController {

    private final StudentRepository studentRepository = new StudentRepository();

    /**
     * ตรวจสอบการ login ของนักศึกษา
     * @return Student ที่ login ผ่าน
     * @throws IllegalArgumentException ถ้ากรอกไม่ครบ หรือ ID/Password ผิด
     */
    public Student login(String id, String password) {
        // ต้องกรอกทั้ง ID และ Password
        if (id == null || id.trim().isEmpty() || password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Please enter your ID and Password!");
        }
        // เทียบกับข้อมูลในไฟล์ Student.csv (trim เหมือนตอนสมัคร)
        Student student = studentRepository.login(id.trim(), password.trim());
        if (student == null) {
            throw new IllegalArgumentException("ID or Password is incorrect!");
        }
        return student;
    }
}
