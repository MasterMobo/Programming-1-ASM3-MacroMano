package PortSystem.User;

import java.util.ArrayList;

public class SystemAdmin extends User {

    public SystemAdmin(String username, String password) {
        super(username, password);
        role = "Admin";
        isBannedOf = new ArrayList<>();
    }

}
