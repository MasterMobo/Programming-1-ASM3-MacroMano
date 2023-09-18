package PortSystem.User;

import java.util.ArrayList;

public class SystemAdmin extends User {

    public SystemAdmin(String username, String password) {
        super(username, password);
        role = "Port Manager";
        isBannedOf = new ArrayList<>();
    }


    @Override
    public boolean isAccessible(String type) {
        if (isBannedOf.contains(type)) {
            return false;
        }
        return true;
    }

}
