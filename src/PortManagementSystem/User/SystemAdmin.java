package PortManagementSystem.User;

import java.util.ArrayList;
import java.util.Arrays;

public class SystemAdmin extends User {

    ArrayList<String> isBannedOf = new ArrayList<>();


    @Override
    public boolean Accessibility(String type) {
        if (isBannedOf.contains(type)) {
            return false;
        }
        return true;
    }
    public SystemAdmin(String username, String password) {
        super(username, password);
    }

}
