package PortManagementSystem.DB;

import PortManagementSystem.Port;
import PortManagementSystem.User.SystemAdmin;
import PortManagementSystem.User.User;
import PortManagementSystem.Utils.SampleData;

import java.io.*;


public class MasterDatabase implements Serializable {
    // Class containing all other Databases
    // The main class for User to interact with the Databases
    private static final String FILE_DIR = "./src/db.obj";
    public Database<Port> ports;
    public UserDatabase users;
    public TripDatabase trips;
    public ContainerDatabase containers;
    public VehicleDatabase vehicles;

    public MasterDatabase() {
        ports = new Database<Port>(this,"p");
        users = new UserDatabase(this);
        trips = new TripDatabase(this);
        containers = new ContainerDatabase(this);
        vehicles = new VehicleDatabase(this);
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


    public static MasterDatabase initDB() {
        if (!fileExists()) {
            MasterDatabase db = SampleData.createSampleDatabase();
            write(db);
            return db;
        }

        return read();
    }

    public static boolean fileExists() {
        return new File(FILE_DIR).exists();
    }

    public static MasterDatabase read() {
        try (FileInputStream fi = new FileInputStream(FILE_DIR);
             ObjectInputStream oi = new ObjectInputStream(fi)) {

            // Read object
            return (MasterDatabase) oi.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Error reading database from file", e);
        }
    }

    public static void write(MasterDatabase db) {
        try (FileOutputStream f = new FileOutputStream(FILE_DIR);
             ObjectOutputStream o = new ObjectOutputStream(f)) {

            // Write object
            o.writeObject(db);
        } catch (IOException e) {
            throw new RuntimeException("Error writing database to file", e);
        }
    }

    public void save() {
        write(this);
    }
}
