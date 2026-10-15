package repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.io.FileWriter;
import java.io.PrintWriter;

import model.RegistrationPeriod;

/**
 * อ่าน/เขียนช่วงเวลาเปิด-ปิดลงทะเบียน
 * CSV format: openAt,closeAt   (ISO เช่น 2026-10-01T08:00)
 * มีแถวข้อมูลแถวเดียว. ถ้าไม่มีไฟล์/อ่านไม่ได้ จะคืน null (= เปิดตลอด)
 */
public class RegistrationPeriodRepository {

    private static final String FILE_PATH = "data/RegistrationPeriod.csv";

    /** อ่านช่วงเวลาจากไฟล์ (คืน null ถ้าไม่มีไฟล์หรืออ่านไม่ได้) */
    public RegistrationPeriod getPeriod() {
        File f = new File(FILE_PATH);
        if (!f.exists()) return null; // ไม่มีไฟล์ = ไม่จำกัดเวลา
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            br.readLine(); // skip header
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] d = line.split(",", -1);
                if (d.length >= 2) {
                    // d[0]=openAt, d[1]=closeAt
                    return new RegistrationPeriod(
                            LocalDateTime.parse(d[0].trim()),
                            LocalDateTime.parse(d[1].trim()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /** บันทึกช่วงเวลาใหม่ทับของเดิม (คืน true ถ้าสำเร็จ) */
    public boolean save(LocalDateTime open, LocalDateTime close) {
        try (PrintWriter out = new PrintWriter(new FileWriter(FILE_PATH, false))) {
            out.println("openAt,closeAt");
            // LocalDateTime.toString() ให้รูปแบบ ISO ตรงกับที่ getPeriod() อ่านได้
            out.println(open + "," + close);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
