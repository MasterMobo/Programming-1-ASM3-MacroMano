package PortManagementSystem.Utils;

import PortManagementSystem.DB.MasterDatabase;
import PortManagementSystem.Port;
import PortManagementSystem.Vehicle.Vehicle;

public class SampleData {
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
