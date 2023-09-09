package PortManagementSystem.DB;

import PortManagementSystem.Port;
import PortManagementSystem.User.SystemAdmin;
import PortManagementSystem.User.User;


public class MasterDatabase {
    // Class containing all other Databases
    // The main class for User to interact with the Databases
    public Database<Port> ports;
    public UserDatabase users;
    public TripDatabase trips;
    public ContainerDatabase containers;
    public VehicleDatabase vehicles;

    public MasterDatabase() {
        ports = new Database<Port>("p");
        users = new UserDatabase();
        trips = new TripDatabase();
        containers = new ContainerDatabase();
        vehicles = new VehicleDatabase();
    }

    // TODO: this is just a test, remove when done
    public static void main(String[] args) {
        MasterDatabase db = new MasterDatabase();
        Port p1 = new Port("SGS", 122.2, 12.3, 647, true);
        Port p2 = new Port("HN", 122.2, 12.3, 647, true);

        db.ports.add(p1);
        db.ports.add(p2);

        db.ports.data.forEach((key, val) -> {
            System.out.println(val);
        });
        System.out.println(db.ports.data.size());

        User admin = new SystemAdmin("dogshit", "piss");
        db.users.add(admin);

        db.users.data.forEach((key, val) -> {
            System.out.println(val);
        });

        System.out.println(db.users.find("dogshit"));
        System.out.println(db.users.find("cum"));
    }
}
