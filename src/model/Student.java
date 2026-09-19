package model;

import java.util.ArrayList;
import java.util.List;

public class Student extends User {

    // ลงทะเบียน
    private List<String> registeredSections;

    public Student(String id, String password, String name) {
        super(id, password, name);
        //TODO Auto-generated constructor stub
        this.registeredSections = new ArrayList<>();
    }

    @Override
    public String getRole() {
        return "STUDENT";
    }

    public void addSection(String sectionId) {
        this.registeredSections.add(sectionId);
    }
    
}
