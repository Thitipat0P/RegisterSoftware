package model;

public abstract class User {

    protected String id;
    protected String name;
    protected String password;

    // Constructor ของคลาสแม่
    public User(String id, String password, String name) {
        this.id = id;
        this.password = password;
        this.name = name;
    }

    // Getters พื้นฐาน
    public String getId() { return id; }
    public String getPassword() { return password; }
    public String getName() { return name; }

    public abstract String getRole();
}
