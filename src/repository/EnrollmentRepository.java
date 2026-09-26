package repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * CSV format: enrollmentId,studentId,courseId,status
 */
public class EnrollmentRepository {

    private static final String FILE_PATH = "data/Enrollment.csv";

    /** Appends an ENROLLED row for this student/course. */
    public boolean enroll(String studentId, String courseId) {
        try (FileWriter fw = new FileWriter(FILE_PATH, true);
             PrintWriter out = new PrintWriter(fw)) {
            String enrollId = "ENR" + System.currentTimeMillis();
            out.println(enrollId + "," + studentId + "," + courseId + ",ENROLLED");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /** Removes the enrollment row matching this student/course. */
    public boolean withdraw(String studentId, String courseId) {
        List<String> keep = new ArrayList<>();
        boolean removed = false;
        File file = new File(FILE_PATH);
        if (!file.exists()) return false;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String header = br.readLine();
            if (header != null) keep.add(header);
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) { keep.add(line); continue; }
                String[] data = line.split(",", -1);
                if (data.length >= 4 && data[1].trim().equals(studentId) && data[2].trim().equals(courseId)) {
                    removed = true; // skip -> effectively deletes it
                } else {
                    keep.add(line);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        if (removed) {
            try (PrintWriter out = new PrintWriter(new FileWriter(file, false))) {
                for (String l : keep) out.println(l);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return removed;
    }

    /** Course IDs this student currently has status ENROLLED for. */
    public List<String> getEnrolledCourseIds(String studentId) {
        List<String> ids = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            br.readLine(); // skip header
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] data = line.split(",", -1);
                if (data.length >= 4 && data[1].trim().equals(studentId) && data[3].trim().equals("ENROLLED")) {
                    ids.add(data[2].trim());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ids;
    }
}
