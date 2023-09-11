package PortManagementSystem.Utils;

import PortManagementSystem.Containers.DryStorage;
import PortManagementSystem.DB.MasterDatabase;
import PortManagementSystem.Port.Port;
import PortManagementSystem.User.SystemAdmin;
import PortManagementSystem.Vehicle.Ship;

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

        Port p1 = new Port("SGS", 122.2, 12.3, 647, true);
        Port p2 = new Port("HN", 122.2, 12.3, 647, true);
        db.ports.add(p1);
        db.ports.add(p2);

        Ship v1 = new Ship("Shit", 345.5, 12.2);
        v1.portId = p1.getId();
        v1.port = p1;
        db.vehicles.add(v1);

        DryStorage c = new DryStorage(12.4);
        c.vehicleId = v1.getId();
        db.containers.add(c);
        return db;
    }
}
