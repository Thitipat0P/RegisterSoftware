package controller;
 
import service.AuthenticationService;
/**
 * ตัวจัดการ "logic ของปุ่ม Login" แยกออกจากหน้าจอ
 * ห้าม import javax.swing ในคลาสนี้ (เรื่องหน้าต่าง/dialog เป็นหน้าที่ของ UI)
 */
public class LoginController {
 
    private final AuthenticationService authService = new AuthenticationService();

 
    /**
     * @return role ("ADMIN" หรือ "STUDENT") ถ้า login ผ่าน
     * @throws IllegalArgumentException ถ้ากรอกไม่ครบ หรือ ID/Password ผิด
     *         (UI เอา e.getMessage() ไปแสดงได้เลย)
     */
    public String login(String id, String password) {
        if (id == null || id.trim().isEmpty() || password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Please enter your ID and Password!");
        }
        String role = authService.login(id.trim(), password.trim());
        if (role == null) {
            throw new IllegalArgumentException("ID or Password is incorrect!");
        }
        return role.trim().toUpperCase();
    }
}