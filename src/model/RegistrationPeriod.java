package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/** ช่วงเวลาที่เปิดให้ลงทะเบียน / ถอนรายวิชา */
public class RegistrationPeriod {

    private static final DateTimeFormatter FMT =
            DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm", Locale.ENGLISH);

    private final LocalDateTime openAt;
    private final LocalDateTime closeAt;

    public RegistrationPeriod(LocalDateTime openAt, LocalDateTime closeAt) {
        this.openAt = openAt;
        this.closeAt = closeAt;
    }

    public LocalDateTime getOpenAt()  { return openAt; }
    public LocalDateTime getCloseAt() { return closeAt; }

    public boolean isOpen(LocalDateTime now) {
        return !now.isBefore(openAt) && !now.isAfter(closeAt);
    }

    public String getStatusText(LocalDateTime now) {
        if (now.isBefore(openAt)) return "Registration opens " + FMT.format(openAt);
        if (now.isAfter(closeAt)) return "Registration closed on " + FMT.format(closeAt);
        return "Registration open until " + FMT.format(closeAt);
    }
}
