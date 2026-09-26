package repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import model.Course;

/**
 * CSV format: courseId,name,credit
 */
public class CourseRepository {

    private static final String FILE_PATH = "data/Course.csv";

    public List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            br.readLine(); // skip header
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] data = line.split(",", -1);
                if (data.length >= 3 && !data[0].trim().isEmpty()) {
                    try {
                        int credit = Integer.parseInt(data[2].trim());
                        courses.add(new Course(data[0].trim(), data[1].trim(), credit));
                    } catch (NumberFormatException ignored) {
                        // skip malformed row
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return courses;
    }

    public Course getCourseById(String courseId) {
        for (Course c : getAllCourses()) {
            if (c.getCourseId().equals(courseId)) {
                return c;
            }
        }
        return null;
    }
}
