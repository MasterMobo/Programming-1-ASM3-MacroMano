package PortSystem.DB;

import PortSystem.Trip.Trip;
import PortSystem.Utils.DateUtils;
import PortSystem.Utils.SampleData;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;


public class MasterDatabase implements Serializable {
    // Class containing all other Databases
    // The main class for CLI to interact with the Databases

    private static final int RECORD_LIFETIME = 7;
    private final PortDatabase ports;
    private final UserDatabase users;
    private final TripDatabase trips;
    private final ContainerDatabase containers;
    private final VehicleDatabase vehicles;

    public MasterDatabase() {
        ports = new PortDatabase(this);
        users = new UserDatabase(this);
        trips = new TripDatabase(this);
        containers = new ContainerDatabase(this);
        vehicles = new VehicleDatabase(this);
    }

    public static MasterDatabase initDB() {
        if (!FileStorage.fileExists()) {
            MasterDatabase db = SampleData.createSampleDatabase();
            FileStorage.write(db);
            return db;
        }

        MasterDatabase db = FileStorage.read();
        db.refresh();
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

    public PortDatabase getPorts() {
        return ports;
    }

    public UserDatabase getUsers() {
        return users;
    }

    public TripDatabase getTrips() {
        return trips;
    }

    public ContainerDatabase getContainers() {
        return containers;
    }

    public VehicleDatabase getVehicles() {
        return vehicles;
    }
}
