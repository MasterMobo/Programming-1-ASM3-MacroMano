package PortSystem.User;

import java.util.ArrayList;
import java.util.Arrays;

public class PortManager extends User {
    private String portID;
    ArrayList<String> isBannedOf = new ArrayList<>( Arrays.asList("port", "vehicle", "user"));


    @Override
    public boolean isAccessible(String type) {
        isBannedOf.add("port");
        isBannedOf.add("vehicle");
        isBannedOf.add("user");
        if (isBannedOf.contains(type)) {
            return false;
        }
        return true;
    }

    public PortManager(String username, String password, String portID) {
        super(username, password);
        this.portID = portID;
    }

    public String getPortID() {
        return portID;
    }
}
