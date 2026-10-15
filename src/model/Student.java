package model;

/**
 * Model เก็บข้อมูลนักศึกษา (immutable: กำหนดค่าครั้งเดียวผ่าน constructor)
 * ไม่เก็บรหัสผ่านไว้ในอ็อบเจกต์ เพื่อความปลอดภัย
 */
public class Student {

    private final String id;     // รหัสนักศึกษา
    private final String name;   // ชื่อ-นามสกุล
    private final String email;  // อีเมล

    public Student(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    // ----- getter -----
    public String getId()    { return id; }
    public String getName()  { return name; }
    public String getEmail() { return email; }
}
