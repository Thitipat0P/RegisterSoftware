package model;

public class Admin extends  User{

    @Override
    public String getRole() {
        return "ADMIN";
    }
    
}
