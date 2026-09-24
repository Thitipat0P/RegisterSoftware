package model;

public class Course {

    private final String courseId;
    private final String courseName;
    private final int credits;

    public Course(String courseId, String courseName, int credits) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.credits = credits;
    }

}
