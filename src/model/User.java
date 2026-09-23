package model;

public abstract class User {
    protected String id;
    protected String name;
    protected String password;

    public User(String id, String password, String name){
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public String getId() { return id; }
    public String getPassword() { return password; }
    public String getName() { return name; }

    public abstract String getRole();
}
