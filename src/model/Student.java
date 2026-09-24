package model;

public class Student extends User {

    public Student(String id, String password, String name) {
        super(id, password, name);
        //TODO Auto-generated constructor stub4
        

    }

    @Override
    public String getRole() {
       return  "STUDENT";
    }
    
}
