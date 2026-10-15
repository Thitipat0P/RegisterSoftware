package model;

/**
 * Model ของนักศึกษา (Student)
 * เก็บ id, ชื่อ, อีเมล — ไม่เก็บรหัสผ่านใน object
 * (รหัสผ่านอยู่ใน data/Student.csv และตรวจที่ StudentRepository)
 */
public class Student {

    private final String id;     // รหัสนักศึกษา (ใช้ล็อกอิน)
    private final String name;   // ชื่อนักศึกษา
    private final String email;  // อีเมล

    public Student(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public String getId()    { return id; }
    public String getName()  { return name; }
    public String getEmail() { return email; }
}
