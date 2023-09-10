package PortManagementSystem.User;
import PortManagementSystem.DB.DatabaseRecord;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements DatabaseRecord, Serializable {
    private String username;
    private String password;

    private ArrayList<User> users;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getId() {
        return username;
    }

    public void setId(String id) {
        username = id;
    }

    public boolean authenticate(String password) {
        return this.password.equals(password);
    }



    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}







