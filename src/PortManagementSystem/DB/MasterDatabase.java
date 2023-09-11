package PortManagementSystem.DB;

import PortManagementSystem.Port;
import PortManagementSystem.User.SystemAdmin;
import PortManagementSystem.User.User;
import PortManagementSystem.Utils.DBUtils;

import java.io.*;
import java.util.Locale;


public class MasterDatabase implements Serializable {
    // Class containing all other Databases
    // The main class for User to interact with the Databases
//    private static final String FILE_DIR = "src/db.obj";

    private static final String FILE_NAME = "db.obj";
    private static final String OS = System.getProperty("os.name", "unknown").toLowerCase(Locale.ENGLISH);

    private static String getFileDir() {
        if (OS.contains("win")) {
            // Running on Windows, use a different path
            return "src" + File.separator + File.separator + FILE_NAME;
        } else if (OS.contains("mac")) {
            // Running on macOS, use the original path
            return FILE_NAME;
        } else {
            // Use a default path for other operating systems
            return "src" + File.separator + File.separator + FILE_NAME;
        }
    }
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
        System.out.println(new File("").getAbsolutePath());
    }


    public static MasterDatabase initDB() {
        if (!fileExists()) {
            MasterDatabase db = new MasterDatabase();
            write(db);
            db = DBUtils.createSampleDatabase();
            write(db);
            return db;
        }

        return read();
    }

    public static boolean fileExists() {
        return new File(getFileDir()).exists();
    }

    public static MasterDatabase read() {
        try (FileInputStream fi = new FileInputStream(getFileDir());
             ObjectInputStream oi = new ObjectInputStream(fi)) {

            // Read object
            return (MasterDatabase) oi.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Error reading database from file", e);
        }
    }

    public static void write(MasterDatabase db) {
        try (FileOutputStream f = new FileOutputStream(getFileDir());
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
