package model;

public class Course {

    private final String courseId;
    private final String name;
    private final int credit;

    public Course(String courseId, String name, int credit) {
        this.courseId = courseId;
        this.name = name;
        this.credit = credit;
    }

    public String getCourseId() { return courseId; }
    public String getName()     { return name; }
    public int getCredit()      { return credit; }
}
