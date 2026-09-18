package model;

public abstract class User {
    protected String id;
    protected String name;
    protected String password;

    public abstract String getRole();
}
