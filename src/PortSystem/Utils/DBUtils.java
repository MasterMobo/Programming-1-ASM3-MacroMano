package PortSystem.Utils;

import PortSystem.Containers.DryStorage;
import PortSystem.DB.MasterDatabase;
import PortSystem.Port.Port;
import PortSystem.Trip.Trip;
import PortSystem.Trip.TripStatus;
import PortSystem.User.SystemAdmin;
import PortSystem.Vehicle.ReeferTruck;
import PortSystem.Vehicle.Ship;
import PortSystem.Vehicle.TankerTruck;
import PortSystem.Vehicle.Truck;

import java.util.Random;

public class DBUtils {
    public static String randKey(int length) {
        // Generate random key of specified length
        Random rnd = new Random();
        StringBuilder key = new StringBuilder();
        for (int i = 0; i < length; i++) {
            key.append(rnd.nextInt(10));
        }
        return key.toString();
    }

    public static MasterDatabase createSampleDatabase() {
        MasterDatabase db = new MasterDatabase();
        SystemAdmin admin = new SystemAdmin("admin", "123");
        db.users.add(admin);

        Port p1 = new Port("SGS", 122.2, 12.3, 3500, true);
        db.ports.add(p1);
        Port p2 = new Port("HN", 204.2, 56.5, 5000, true);
        db.ports.add(p2);

        Ship v1 = new Ship("Dakota-102", 1000.0, 500.0);
        v1.portId = p1.getId();
        db.vehicles.add(v1);


        Truck v2 = new Truck("Princeton-556", 340.0, 45.0);
        v2.portId = p1.getId();
        db.vehicles.add(v2);

        TankerTruck v3 = new TankerTruck("Hulk-014", 300.0, 50.0);
        v3.portId = p2.getId();
        db.vehicles.add(v3);

        ReeferTruck v4 = new ReeferTruck("Johnson-504", 270.0, 70.0);
        v4.portId = p2.getId();
        db.vehicles.add(v4);

        DryStorage c = new DryStorage(12.4);
        c.vehicleId = v1.getId();
        db.containers.add(c);

        Trip trip1 = new Trip(v1.getId(), p1.getId(), p2.getId(), DateUtils.toLocalDate("11/09/2023"), DateUtils.toLocalDate("20/10/2023"), 1300, 655.4, TripStatus.PROCESSING);
        db.trips.add(trip1);

        Trip trip2 = new Trip(v2.getId(), p1.getId(), p2.getId(), DateUtils.toLocalDate("20/01/2023"), DateUtils.toLocalDate("05/02/2023"), 1300, 734.6, TripStatus.FULFILLED);
        db.trips.add(trip2);

        Trip trip3 = new Trip(v3.getId(), p2.getId(), p1.getId(), DateUtils.toLocalDate("24/04/2022"), DateUtils.toLocalDate("30/04/2022"), 1300, 564.4, TripStatus.FULFILLED);
        db.trips.add(trip3);


        return db;
    }
}
