package controller;

import repository.AdminRepository;
import repository.StudentRepository;

/**
 * Controller สำหรับสมัครบัญชีนักศึกษาใหม่
 * ตรวจสอบข้อมูลที่กรอก แล้วส่งให้ StudentRepository บันทึกลงไฟล์
 */
public class RegisterController {

    private final StudentRepository studentRepository = new StudentRepository();
    private final AdminRepository adminRepository = new AdminRepository();

    /**
     * สมัครบัญชีใหม่
     * @throws IllegalArgumentException ถ้ากรอกไม่ครบ, มีคอมมา, รหัสผ่านไม่ตรงกัน, หรือ ID ซ้ำ
     */
    public void register(String id, String name, String email, String password, String confirmPassword) {
        // ต้องกรอกทุกช่อง
        if (isBlank(id) || isBlank(name) || isBlank(email) || isBlank(password)) {
            throw new IllegalArgumentException("Please fill in all fields.");
        }
        // CSV ใช้คอมมาแบ่งคอลัมน์ ถ้ามีคอมมาในข้อมูลไฟล์จะพัง
        if (id.contains(",") || name.contains(",") || email.contains(",")) {
            throw new IllegalArgumentException("ID/Name/Email must not contain commas.");
        }
        // รหัสผ่านกับช่องยืนยันต้องตรงกัน
        if (!password.equals(confirmPassword)) {
            throw new IllegalArgumentException("Passwords do not match!");
        }
        // ห้ามใช้ ID ซ้ำกับ admin
        if (adminRepository.exists(id.trim())) {
            throw new IllegalArgumentException("This Student ID is not allowed.");
        }
        // ห้ามใช้ ID ซ้ำกับนักศึกษาที่สมัครไว้แล้ว
        if (studentRepository.exists(id.trim())) {
            throw new IllegalArgumentException("This Student ID is already registered.");
        }
        // trim รหัสผ่านเหมือนตอน login (LoginController ก็ trim)
        boolean ok = studentRepository.register(id.trim(), name.trim(), email.trim(), password.trim());
        if (!ok) {
            throw new IllegalArgumentException("Could not create account. Please try again.");
        }
    }

    /** ตรวจว่าข้อความเป็น null หรือว่างเปล่า */
    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
