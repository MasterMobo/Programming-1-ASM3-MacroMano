package PortSystem.DB;

import PortSystem.Containers.Container;
import PortSystem.Port.Port;
import PortSystem.Vehicle.Ship;
import PortSystem.Vehicle.Truck;
import PortSystem.Vehicle.Vehicle;

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

    public Float totalConsumption(String vehicleId, String portID) {
        if (!portExists(portID)) return null;
        Vehicle vehicle = mdb.vehicles.find(vehicleId);
        if (vehicle == null) return null;
        Port nextPort = mdb.ports.find(portID);
        Port currentPort = vehicle.port;
        double travelDistance = currentPort.getDist(nextPort);
        float totalConsumption = vehicle.getTotalFuelConsumption();

        if (vehicle instanceof Ship) {
            for (Container container : vehicle.loadedContainers) {
                totalConsumption += (float) (vehicle.getTotalFuelConsumption() + container.getShipFuelConsumption() * container.getWeight() * travelDistance);
            }

        } else if (vehicle instanceof Truck) {
            for (Container container : vehicle.loadedContainers) {
                totalConsumption += container.getTruckFuelConsumption() * container.getWeight() * travelDistance;
            }
        }
        return totalConsumption;
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
        add(vehicle);
        return vehicle;
    }

    @Override
    public Vehicle updateRecord(String id) {
        Vehicle vehicle = super.updateRecord(id);

        Scanner scanner = new Scanner(System.in);

        vehicle.setName(getInputString("Name: ", vehicle.getName(), scanner));

        vehicle.setCarryCapacity(getInputDouble("Carry Weight Capacity: ", vehicle.getCarryCapacity(), scanner));

        vehicle.setCurCarryWeight(getInputDouble("Current Carry Weight: ", vehicle.getCurCarryWeight(), scanner));

        vehicle.setFuelCapacity(getInputDouble("Fuel Capacity: ", vehicle.getFuelCapacity(), scanner));

        vehicle.setCurFuelConsumption(getInputDouble("Current Fuel Consumption: ", vehicle.getCurFuelConsumption(), scanner));

        System.out.println("Updated record: " + vehicle);
        mdb.save();
        return vehicle;
    }
}
