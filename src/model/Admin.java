package model;

/**
 * Model ของผู้ดูแลระบบ (Admin)
 * เก็บเฉพาะ id และชื่อ — ไม่เก็บรหัสผ่านไว้ใน object เพื่อความปลอดภัย
 * (รหัสผ่านอยู่ในไฟล์ data/Admin.csv และตรวจที่ AdminRepository)
 */
public class Admin {
    private final String id;    // รหัสผู้ดูแลระบบ (ใช้ล็อกอิน)
    private final String name;  // ชื่อที่แสดงบนหน้าจอ

    public Admin(String id, String name) { this.id = id; this.name = name; }

    public String getId()   { return id; }
    public String getName() { return name; }
}
