package PortSystem.DB;

import PortSystem.Trip.Trip;
import PortSystem.Utils.DBUtils;
import PortSystem.Utils.DateUtils;
import PortSystem.Utils.SampleData;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;


public class MasterDatabase implements Serializable {
    // Class containing all other Databases
    // The main class for User to interact with the Databases

    private static final int RECORD_LIFETIME = 7;

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
            // TODO records can only be kept for 7 days, should sample data consider this?
            MasterDatabase db = SampleData.createSampleDatabase();
            FileStorage.write(db);
            return db;
        }

        MasterDatabase db = FileStorage.read();
        // TODO will comment out for now, put it back in when shipped
//        db.refresh();
        return db;
    }

    public void save() {
        FileStorage.write(this);
    }

    private void refresh() {
        // Deletes expired records
        LocalDate now = LocalDate.now();

        ArrayList<String> deleteIds = new ArrayList<>();
        for (Trip trip: trips.data.values()) {
            if (DateUtils.daysBetween(trip.getDepartDate(), now) > RECORD_LIFETIME) {
                deleteIds.add(trip.getId());
            }
        }

        for (String id: deleteIds) {
            trips.delete(id);
        }
    }
}
