package model;

public class Section {

    private final String sectionId;
    private final String courseId;   // อ้างถึง Course ด้วย id (ตรงกับที่เก็บใน section.csv)
    private final int maxSeats;
    private int availableSeats;

    /** ใช้ตอนโหลดจากไฟล์ (รู้จำนวนที่นั่งว่างอยู่แล้ว) */
    public Section(String sectionId, String courseId, int maxSeats, int availableSeats) {
        if (sectionId == null || sectionId.trim().isEmpty()) {
            throw new IllegalArgumentException("Section ID must not be empty");
        }
        if (courseId == null || courseId.trim().isEmpty()) {
            throw new IllegalArgumentException("Course ID must not be empty");
        }
        if (maxSeats <= 0) {
            throw new IllegalArgumentException("Max seats must be greater than 0");
        }
        if (availableSeats < 0 || availableSeats > maxSeats) {
            throw new IllegalArgumentException("Available seats must be between 0 and max seats");
        }
        this.sectionId = sectionId.trim();
        this.courseId = courseId.trim();
        this.maxSeats = maxSeats;
        this.availableSeats = availableSeats;
    }

    /** ใช้ตอน admin สร้าง section ใหม่ (ที่นั่งว่างเท่ากับที่นั่งทั้งหมด) */
    public Section(String sectionId, String courseId, int maxSeats) {
        this(sectionId, courseId, maxSeats, maxSeats);
    }

    public String getSectionId()   { return sectionId; }
    public String getCourseId()    { return courseId; }
    public int getMaxSeats()       { return maxSeats; }
    public int getAvailableSeats() { return availableSeats; }

    public boolean isFull() {
        return availableSeats <= 0;
    }

    /** ลงทะเบียนเรียน: ลดที่นั่งว่างลง 1 (โยน exception ถ้าเต็ม) */
    public void enrollStudent() {
        if (isFull()) {
            throw new IllegalStateException("Section " + sectionId + " is full");
        }
        availableSeats--;
    }

    /** ถอนวิชา: คืนที่นั่งว่างกลับ 1 */
    public void dropStudent() {
        if (availableSeats >= maxSeats) {
            throw new IllegalStateException("Section " + sectionId + " has no enrolled students to drop");
        }
        availableSeats++;
    }
}