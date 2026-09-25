package model;

public class Course {

    private final String courseId;
    private final String courseName;
    private final int credits;

    public Course(String courseId, String courseName, int credits) {
        if (courseId == null || courseId.trim().isEmpty()) {
            throw new IllegalArgumentException("Course ID must not be empty");
        }
        if (courseName == null || courseName.trim().isEmpty()) {
            throw new IllegalArgumentException("Course name must not be empty");
        }
        if (credits <= 0) {
            throw new IllegalArgumentException("Credits must be greater than 0");
        }
        this.courseId = courseId.trim();
        this.courseName = courseName.trim();
        this.credits = credits;
    }

    public String getCourseId()   { return courseId; }
    public String getCourseName() { return courseName; }
    public int getCredits()       { return credits; }
}
