package PortManagementSystem.DB;

import PortManagementSystem.Containers.Container;
import PortManagementSystem.Containers.DryStorage;
import PortManagementSystem.Port;
import PortManagementSystem.User.SystemAdmin;
import PortManagementSystem.User.User;

public class MasterDatabase {
//    private Database<Container> containers;
    private Database<Port> ports;
    private Database<User> users;

    public MasterDatabase() {
//        containers = new Database<Container>("c");
        ports = new Database<Port>("p");
        users = new UserDatabase();
    }

    // TODO: this is just a test, remove when ready
    public static void main(String[] args) {
        MasterDatabase db = new MasterDatabase();
        Port p1 = new Port("SGS", 122.2, 12.3, 647, true);
        Port p2 = new Port("HN", 122.2, 12.3, 647, true);

        db.ports.createRecord(p1);
        db.ports.createRecord(p2);

        db.ports.data.forEach((key, val) -> {
            System.out.println(val);
        });
        System.out.println(db.ports.data.size());

        User admin = new SystemAdmin("dogshit", "piss");
        db.users.createRecord(admin);

        db.users.data.forEach((key, val) -> {
            System.out.println(val);
        });

        System.out.println(db.users.readRecord("dogshit"));
        System.out.println(db.users.readRecord("cum"));
    }
}
