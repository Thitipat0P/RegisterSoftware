package repository;

import java.io.BufferedReader;
import java.io.FileReader;

import model.Admin;

/** อ่านข้อมูลผู้ดูแลระบบจากไฟล์ CSV   format: id,name,password */
public class AdminRepository {

    // ตำแหน่งไฟล์ข้อมูล (อ้างอิงจากโฟลเดอร์ที่รันโปรแกรม)
    private static final String FILE_PATH = "data/Admin.csv";

    /** Returns the Admin if id/password match, otherwise null. */
    public Admin login(String id, String password) {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            br.readLine(); // skip header
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue; // ข้ามบรรทัดว่าง
                // split ด้วย -1 เพื่อให้คอลัมน์ว่างท้ายบรรทัดไม่ถูกตัดทิ้ง
                String[] d = line.split(",", -1);
                // d[0]=id, d[1]=name, d[2]=password
                if (d.length >= 3 && d[0].trim().equals(id) && d[2].trim().equals(password)) {
                    return new Admin(d[0].trim(), d[1].trim());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // ไม่พบ หรืออ่านไฟล์ไม่ได้
    }

    /** True if an admin with this id exists (ใช้กันนักศึกษาสมัครด้วย ID เดียวกับ admin) */
    public boolean exists(String id) {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            br.readLine(); // skip header
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] d = line.split(",", -1);
                // เทียบเฉพาะ id โดยไม่สนตัวพิมพ์เล็ก/ใหญ่
                if (d.length >= 1 && d[0].trim().equalsIgnoreCase(id)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
