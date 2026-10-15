package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/** ช่วงเวลาที่เปิดให้ลงทะเบียน / ถอนรายวิชา */
public class RegistrationPeriod {

    // รูปแบบวันที่ที่ใช้แสดงผล เช่น "01 Oct 2026 08:00"
    private static final DateTimeFormatter FMT =
            DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm", Locale.ENGLISH);

    private final LocalDateTime openAt;   // เวลาเปิดลงทะเบียน
    private final LocalDateTime closeAt;  // เวลาปิดลงทะเบียน

    public RegistrationPeriod(LocalDateTime openAt, LocalDateTime closeAt) {
        this.openAt = openAt;
        this.closeAt = closeAt;
    }

    public LocalDateTime getOpenAt()  { return openAt; }
    public LocalDateTime getCloseAt() { return closeAt; }

    /** เวลา now อยู่ในช่วงเปิดลงทะเบียนหรือไม่ (รวมวินาทีที่เปิดและปิดพอดี) */
    public boolean isOpen(LocalDateTime now) {
        return !now.isBefore(openAt) && !now.isAfter(closeAt);
    }

    /** ข้อความสถานะสำหรับแสดงบนหน้าจอ: ยังไม่เปิด / ปิดแล้ว / กำลังเปิดอยู่ */
    public String getStatusText(LocalDateTime now) {
        if (now.isBefore(openAt)) return "Registration opens " + FMT.format(openAt);
        if (now.isAfter(closeAt)) return "Registration closed on " + FMT.format(closeAt);
        return "Registration open until " + FMT.format(closeAt);
    }
}
