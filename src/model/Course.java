package model;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.Locale;

/**
 * Model ของรายวิชา (Course)
 * เก็บรหัสวิชา ชื่อ หน่วยกิต และเวลาเรียน (วัน + เวลาเริ่ม + เวลาเลิก)
 * ถ้ายังไม่กำหนดเวลาเรียน ค่า day/startTime/endTime จะเป็น null (แสดงเป็น "TBA")
 */
public class Course {

    private final String courseId;
    private final String name;
    private final int credit;
    private final DayOfWeek day;       // วันที่เรียน (null = ยังไม่กำหนด)
    private final LocalTime startTime; // เวลาเริ่มเรียน
    private final LocalTime endTime;   // เวลาเลิกเรียน

    /** สร้างวิชาที่ยังไม่กำหนดเวลาเรียน (TBA) */
    public Course(String courseId, String name, int credit) {
        this(courseId, name, credit, null, null, null);
    }

    /** สร้างวิชาพร้อมเวลาเรียนครบถ้วน */
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

    /** มีการกำหนดเวลาเรียนครบทั้งวันและเวลาหรือไม่ */
    public boolean hasSchedule() {
        return day != null && startTime != null && endTime != null;
    }

    /**
     * เวลาเรียนชนกันหรือไม่: วันเดียวกัน และช่วงเวลาซ้อนทับกัน
     * (เลิก 12:00 กับเริ่ม 12:00 ไม่ถือว่าชน)
     * ถ้าวิชาใดวิชาหนึ่งไม่มีเวลาเรียน (TBA) ถือว่าไม่ชน
     */
    public boolean conflictsWith(Course other) {
        if (other == null || !hasSchedule() || !other.hasSchedule()) return false;
        if (day != other.day) return false; // คนละวัน ไม่ชนแน่นอน
        // เงื่อนไขช่วงเวลาซ้อนทับ: A เริ่มก่อน B เลิก และ B เริ่มก่อน A เลิก
        return startTime.isBefore(other.endTime) && other.startTime.isBefore(endTime);
    }

    /** เช่น "MON 09:00-12:00" หรือ "TBA" ถ้ายังไม่กำหนด */
    public String getScheduleText() {
        if (!hasSchedule()) return "TBA";
        return day.getDisplayName(TextStyle.SHORT, Locale.ENGLISH).toUpperCase()
                + " " + startTime + "-" + endTime;
    }
}
