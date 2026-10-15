package controller;

import repository.StudentRepository;

/**
 * logic ของการสมัครบัญชีนักศึกษาใหม่ (ห้าม import javax.swing)
 */
public class RegisterController {

    private final StudentRepository studentRepository = new StudentRepository();

    /**
     * @throws IllegalArgumentException ถ้ากรอกไม่ครบ, รหัสผ่านไม่ตรงกัน, หรือ ID ซ้ำ
     */
    public void register(String id, String name, String email, String password, String confirmPassword) {
        // 1) ต้องกรอกครบทุกช่อง
        if (isBlank(id) || isBlank(name) || isBlank(email) || isBlank(password)) {
            throw new IllegalArgumentException("Please fill in all fields.");
        }
        // 2) รหัสผ่านและยืนยันรหัสผ่านต้องตรงกัน
        if (!password.equals(confirmPassword)) {
            throw new IllegalArgumentException("Passwords do not match!");
        }
        // 3) รหัสนักศึกษาต้องไม่ซ้ำกับที่มีอยู่
        if (studentRepository.exists(id.trim())) {
            throw new IllegalArgumentException("This Student ID is already registered.");
        }
        // 4) บันทึกลงไฟล์
        boolean ok = studentRepository.register(id.trim(), name.trim(), email.trim(), password);
        if (!ok) {
            throw new IllegalArgumentException("Could not create account. Please try again.");
        }
    }

    /** ตรวจว่าข้อความเป็น null หรือว่างเปล่า */
    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
