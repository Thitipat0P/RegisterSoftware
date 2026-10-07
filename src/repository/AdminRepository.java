package repository;

import java.io.BufferedReader;
import java.io.FileReader;

import model.Admin;

/** CSV format: id,name,password */
public class AdminRepository {

    private static final String FILE_PATH = "data/Admin.csv";

    /** Returns the Admin if id/password match, otherwise null. */
    public Admin login(String id, String password) {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            br.readLine(); // skip header
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] d = line.split(",", -1);
                if (d.length >= 3 && d[0].trim().equals(id) && d[2].trim().equals(password)) {
                    return new Admin(d[0].trim(), d[1].trim());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
