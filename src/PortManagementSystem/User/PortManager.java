package PortManagementSystem.User;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class PortManager extends User {
    private String portName;
    ArrayList<String> isBannedOf = new ArrayList<>( Arrays.asList("port", "vehicle", "user"));


    @Override
    public boolean Accessibility(String type) {
        isBannedOf.add("port");
        isBannedOf.add("vehicle");
        isBannedOf.add("user");
        if (isBannedOf.contains(type)) {
            return false;
        }
        return true;
    }

    public PortManager(String username, String password, String portName) {
        super(username, password);
        this.portName = portName;
    }
}
