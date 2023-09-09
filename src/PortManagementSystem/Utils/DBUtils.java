package PortManagementSystem.Utils;

import PortManagementSystem.DB.MasterDatabase;
import PortManagementSystem.Port;
import PortManagementSystem.Vehicle.Vehicle;

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
        Port p1 = new Port("SGS", 122.2, 12.3, 647, true);
        Port p2 = new Port("HN", 122.2, 12.3, 647, true);
        db.ports.add(p1);
        db.ports.add(p2);

        Vehicle v1 = new Vehicle("Shit", "piss", p1, 12., 23.);
        db.vehicles.add(v1);
        return db;
    }
}
