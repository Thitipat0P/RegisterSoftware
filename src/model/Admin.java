package model;

public class Admin extends  User{

    public Admin(String id, String password, String name) {
        super(id, password, name);
        //TODO Auto-generated constructor stub
    }

    @Override
    public String getRole() {
       return  "ADMIN";
    }
    
}
