package model;

public class Section {

    private final String sectionId;
    private final String courseId;   // อ้างถึง Course ด้วย id (ตรงกับที่เก็บใน section.csv)
    private final int maxSeats;
    private int availableSeats;  

    public Section(String sectionId, String courseId, int maxSeats) {
        this.sectionId = sectionId;
        this.courseId = courseId;
        this.maxSeats = maxSeats;
        this.availableSeats = maxSeats;  // เริ่มต้น availableSeats เท่ากับ maxSeats
    }
    public void  enrollStudent(){}  //ลงทะเบียนเรียน
  
}


