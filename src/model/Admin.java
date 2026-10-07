package model;

public class Admin {
    private final String id;
    private final String name;

    public Admin(String id, String name) { this.id = id; this.name = name; }

    public String getId()   { return id; }
    public String getName() { return name; }
}
