package model;

import java.util.List;

public class Instructor extends User {
    // สิ่งที่อาจารย์มีเพิ่มขึ้นมา
    private List<String> teachingSections;
    
    public Instructor(String id, String password, String name) {
        super(id, password, name);
        //TODO Auto-generated constructor stub
    }

    @Override
    public String getRole() {
        return "INSTRUCTOR";
    }

    // เมธอดเฉพาะของอาจารย์
    public List<String> getTeachingSections() {
        return teachingSections;
    }
    
}
