package PortSystem.DB;

import PortSystem.Utils.DBUtils;

import java.io.*;


public class MasterDatabase implements Serializable {
    // Class containing all other Databases
    // The main class for User to interact with the Databases
//    private static final String FILE_DIR = "src/db.obj";

    public PortDatabase ports;
    public UserDatabase users;
    public TripDatabase trips;
    public ContainerDatabase containers;
    public VehicleDatabase vehicles;

    public MasterDatabase() {
        ports = new PortDatabase(this);
        users = new UserDatabase(this);
        trips = new TripDatabase(this);
        containers = new ContainerDatabase(this);
        vehicles = new VehicleDatabase(this);
    }

    public static MasterDatabase initDB() {
        if (!FileStorage.fileExists()) {
            MasterDatabase db = DBUtils.createSampleDatabase();
            FileStorage.write(db);
            return db;
        }

        return FileStorage.read();
    }

    public void save() {
        FileStorage.write(this);
    }

}