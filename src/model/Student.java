package model;

public class Student extends User {

    public Student(String id, String password, String name) {
        super(id, password, name);
        //TODO Auto-generated constructor stub
    }

    @Override
    public String getRole() {
       return  "STUDENT";
    }
    
}
