package controller;

import repository.StudentRepository;

public class RegisterController {

    private final StudentRepository studentRepository = new StudentRepository();

    /**
     * @throws IllegalArgumentException ถ้ากรอกไม่ครบ, รหัสผ่านไม่ตรงกัน, หรือ ID ซ้ำ
     */
    public void register(String id, String name, String email, String password, String confirmPassword) {
        if (isBlank(id) || isBlank(name) || isBlank(email) || isBlank(password)) {
            throw new IllegalArgumentException("Please fill in all fields.");
        }
        if (!password.equals(confirmPassword)) {
            throw new IllegalArgumentException("Passwords do not match!");
        }
        if (studentRepository.exists(id.trim())) {
            throw new IllegalArgumentException("This Student ID is already registered.");
        }
        boolean ok = studentRepository.register(id.trim(), name.trim(), email.trim(), password);
        if (!ok) {
            throw new IllegalArgumentException("Could not create account. Please try again.");
        }
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
