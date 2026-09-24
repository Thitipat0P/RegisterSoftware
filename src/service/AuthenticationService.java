 package service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class AuthenticationService {
    private static final String FILE_PATH = "data/users.csv";
    public String login(String username, String password) {
        String line = "";
        String splitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            br.readLine(); // ข้ามบรรทัดแรกที่เป็น Header (id,password,name,role)

            // อ่านทีละบรรทัดจนกว่าจะหมดไฟล์
            while ((line = br.readLine()) != null) {
                String[] userRecord = line.split(splitBy);
                
                String csvId = userRecord[0];
                String csvPassword = userRecord[1];
                String csvRole = userRecord[3];

                // ถ้าไอดีและรหัสผ่านตรงกัน ให้คืนค่า Role กลับไป
                if (csvId.equals(username) && csvPassword.equals(password)) {
                    return csvRole; 
                }
            }
        } catch (IOException e) {
            System.out.println("Cant read file CSV: " + e.getMessage());
        }
        
        // ถ้าหาไม่เจอ หรือรหัสผิด จะส่งค่า null กลับไป
        return null; 
    }
}