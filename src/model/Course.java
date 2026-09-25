package model;

public class Course {

    private  final String courseId;
    private  final String courseName;
    private  final int credits;

    public Course(String courseId, String name, int credits) {
        this.courseId = courseId;
        this.courseName = name;
        this.credits = credits;
    }

}
