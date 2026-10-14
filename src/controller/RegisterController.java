package controller;

import repository.AdminRepository;
import repository.StudentRepository;

public class RegisterController {

    private final StudentRepository studentRepository = new StudentRepository();
    private final AdminRepository adminRepository = new AdminRepository();

    /**
     * @throws IllegalArgumentException ถ้ากรอกไม่ครบ, มีคอมมา, รหัสผ่านไม่ตรงกัน, หรือ ID ซ้ำ
     */
    public void register(String id, String name, String email, String password, String confirmPassword) {
        if (isBlank(id) || isBlank(name) || isBlank(email) || isBlank(password)) {
            throw new IllegalArgumentException("Please fill in all fields.");
        }
        // CSV ใช้คอมมาแบ่งคอลัมน์ ถ้ามีคอมมาในข้อมูลไฟล์จะพัง
        if (id.contains(",") || name.contains(",") || email.contains(",")) {
            throw new IllegalArgumentException("ID/Name/Email must not contain commas.");
        }
        if (!password.equals(confirmPassword)) {
            throw new IllegalArgumentException("Passwords do not match!");
        }
        // ห้ามใช้ ID ซ้ำกับ admin
        if (adminRepository.exists(id.trim())) {
            throw new IllegalArgumentException("This Student ID is not allowed.");
        }
        if (studentRepository.exists(id.trim())) {
            throw new IllegalArgumentException("This Student ID is already registered.");
        }
        // trim รหัสผ่านเหมือนตอน login (LoginController ก็ trim)
        boolean ok = studentRepository.register(id.trim(), name.trim(), email.trim(), password.trim());
        if (!ok) {
            throw new IllegalArgumentException("Could not create account. Please try again.");
        }
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
