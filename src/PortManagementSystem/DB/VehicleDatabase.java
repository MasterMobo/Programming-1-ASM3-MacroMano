package PortManagementSystem.DB;

import PortManagementSystem.Port;
import PortManagementSystem.Vehicle.Ship;
import PortManagementSystem.Vehicle.Truck;
import PortManagementSystem.Vehicle.Vehicle;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class VehicleDatabase extends Database<Vehicle> {
    public VehicleDatabase(MasterDatabase mdb) {
        super(mdb, "v");
    }

    private boolean portExists(String portId) {
        return mdb.ports.find(portId) != null;
    }

    public ArrayList<Vehicle> fromPort(String portID) {
        if (!portExists(portID)) return null;

        ArrayList<Vehicle> res = new ArrayList<>();

        for (Map.Entry<String, Vehicle> set: data.entrySet()) {
            Vehicle vehicle = set.getValue();
            if (Objects.equals(vehicle.portId, portID)) {
                res.add(vehicle);
            }
        }
        return res;
    }

    public ArrayList<Vehicle> shipsFromPort(String portID) {
        if (!portExists(portID)) return null;

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
        if (!portExists(portID)) return null;

        ArrayList<Vehicle> res = new ArrayList<>();

        for (Map.Entry<String, Vehicle> set: data.entrySet()) {
            Vehicle vehicle = set.getValue();
            if (Objects.equals(vehicle.portId, portID) && vehicle instanceof Truck) {
                res.add(vehicle);
            }
        }
        return res;
    }

    @Override
    public Vehicle createRecord(Vehicle vehicle) {
        if (vehicle == null) return null;
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter port ID: ");
        Port p = mdb.ports.find(scanner.nextLine().trim());
        if (p == null) return null;

        // TODO restructure port in Vehicle (only store portId, not port obj)
        vehicle.portId = p.getId();
        vehicle.port = p;
        add(vehicle);
        return vehicle;
    }

}
