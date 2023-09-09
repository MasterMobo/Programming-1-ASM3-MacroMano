package PortManagementSystem.DB;

import PortManagementSystem.Port;
import PortManagementSystem.Vehicle.Ship;
import PortManagementSystem.Vehicle.Truck;
import PortManagementSystem.Vehicle.Vehicle;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class VehicleDatabase extends Database<Vehicle> {
    public VehicleDatabase() {
        super("v");
    }

    public ArrayList<Vehicle> fromPort(String portID) {
        ArrayList<Vehicle> res = new ArrayList<>();

        for (Map.Entry<String, Vehicle> set: data.entrySet()) {
            String key = set.getKey();
            Vehicle vehicle = set.getValue();
            if (Objects.equals(vehicle.portId, portID)) {
                res.add(vehicle);
            }
        }
        return res;
    }

    public ArrayList<Vehicle> shipsFromPort(String portID) {
        ArrayList<Vehicle> res = new ArrayList<>();

        for (Map.Entry<String, Vehicle> set: data.entrySet()) {
            Vehicle vehicle = set.getValue();
            if (Objects.equals(vehicle.portId, portID) && vehicle instanceof Ship) {
                res.add(vehicle);
            }
        }
        return res;
    }

    public ArrayList<Vehicle> trucksFromPort(String portID) {
        ArrayList<Vehicle> res = new ArrayList<>();

        for (Map.Entry<String, Vehicle> set: data.entrySet()) {
            Vehicle vehicle = set.getValue();
            if (Objects.equals(vehicle.portId, portID) && vehicle instanceof Truck) {
                res.add(vehicle);
            }
        }
        return res;
    }

// TODO: testing
    public static void main(String[] args) {
        MasterDatabase db = new MasterDatabase();
        Port p1 = new Port("SGS", 122.2, 12.3, 647, true);
        db.ports.add(p1);
        Port p2 = new Port("SGS", 122.2, 12.3, 647, true);
        db.ports.add(p2);

        Vehicle v1 = new Vehicle("Shit", "piss", p1, 12., 23.);
        db.vehicles.add(v1);

        Vehicle v2 = new Vehicle("Shit", "cum", p1, 12., 23.);
        db.vehicles.add(v2);

        Vehicle v3 = new Vehicle("Shit", "ass", p2, 12., 23.);
        db.vehicles.add(v3);
        db.vehicles.display();
        System.out.println(db.vehicles.fromPort(p1.getId()));

    }
}
