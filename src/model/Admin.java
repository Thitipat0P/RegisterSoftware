package model;

/** Model เก็บข้อมูลผู้ดูแลระบบ (ไม่เก็บรหัสผ่านในอ็อบเจกต์) */
public class Admin {
    private final String id;    // รหัสผู้ดูแล
    private final String name;  // ชื่อผู้ดูแล

    public Admin(String id, String name) { this.id = id; this.name = name; }

    public String getId()   { return id; }
    public String getName() { return name; }
}
