package model;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.Locale;

public class Course {

    private final String courseId;
    private final String name;
    private final int credit;
    private final DayOfWeek day;       // วันที่เรียน (null = ยังไม่กำหนด)
    private final LocalTime startTime; // เวลาเริ่มเรียน
    private final LocalTime endTime;   // เวลาเลิกเรียน

    public Course(String courseId, String name, int credit) {
        this(courseId, name, credit, null, null, null);
    }

    public Course(String courseId, String name, int credit,
                  DayOfWeek day, LocalTime startTime, LocalTime endTime) {
        this.courseId = courseId;
        this.name = name;
        this.credit = credit;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getCourseId()    { return courseId; }
    public String getName()        { return name; }
    public int getCredit()         { return credit; }
    public DayOfWeek getDay()      { return day; }
    public LocalTime getStartTime(){ return startTime; }
    public LocalTime getEndTime()  { return endTime; }

    public boolean hasSchedule() {
        return day != null && startTime != null && endTime != null;
    }

    /**
     * เวลาเรียนชนกันหรือไม่: วันเดียวกัน และช่วงเวลาซ้อนทับกัน
     * (เลิก 12:00 กับเริ่ม 12:00 ไม่ถือว่าชน)
     */
    public boolean conflictsWith(Course other) {
        if (other == null || !hasSchedule() || !other.hasSchedule()) return false;
        if (day != other.day) return false;
        return startTime.isBefore(other.endTime) && other.startTime.isBefore(endTime);
    }

    /** เช่น "MON 09:00-12:00" หรือ "TBA" ถ้ายังไม่กำหนด */
    public String getScheduleText() {
        if (!hasSchedule()) return "TBA";
        return day.getDisplayName(TextStyle.SHORT, Locale.ENGLISH).toUpperCase()
                + " " + startTime + "-" + endTime;
    }
}
