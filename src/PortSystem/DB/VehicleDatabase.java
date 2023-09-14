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
    private boolean vehicleExists(String vehicleId) {
        return mdb.vehicles.find(vehicleId) != null;
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
        Vehicle vehicle = mdb.vehicles.find(vehicleId);
        if (vehicle == null) return null;

        Port nextPort = mdb.ports.find(portID);
        if (nextPort == null) return null;

        if (!mdb.ports.exists(vehicle.portId)) {    // Using exists instead of find b/c find() could print not found message
            System.out.println("Vehicle port can't be found");
            return null;
        }

        Port currentPort = mdb.ports.find(vehicle.portId);
        ArrayList<Container> loadedContainers = mdb.containers.fromVehicle(vehicleId);

        return vehicle.calculateTotalConsumption(currentPort, nextPort, loadedContainers);
    }

    public void move(String vehicleId, String portID) {
        Vehicle vehicle = mdb.vehicles.find(vehicleId);
        if (vehicle == null) return;

        Port nextPort = mdb.ports.find(portID);
        if (nextPort == null) return;

        if (!mdb.ports.exists(vehicle.portId)) {   
            System.out.println("Vehicle's port can't be found");
            return;
        }

        Double totalConsumption = Double.valueOf(mdb.vehicles.totalConsumption(vehicleId, nextPort.getId()));

        if (!(totalConsumption < vehicle.getCurfuelCapacity())) {
            System.out.println("Vehicle not allowed to move due to fuel capacity exceeding");
            return;
        }
//        Changing fuel for refuel method to work
        vehicle.setCurfuelCapacity(vehicle.deductFuel(vehicle));

//        new port
        vehicle.portId = nextPort.getId();
    }

//    Assuming vehicle is always at a port
    public void refuelVehicle(String vehicleId) {
        Vehicle vehicle = mdb.vehicles.find(vehicleId);
        if (vehicle == null) return;

        vehicle.setCurfuelCapacity(vehicle.getFuelCapacity());
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
        System.out.println("Created Record: " + vehicle);
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

        vehicle.portId = getInputId("Port ID: ", vehicle.portId, scanner, mdb.ports);

        vehicle.setCurfuelCapacity(vehicle.getFuelCapacity());
        System.out.println("Updated record: " + vehicle);
        mdb.save();
        return vehicle;
    }

    public void showInfo(String vehicleID) {
        if (!vehicleExists(vehicleID)) {
            System.out.println("Invalid Vehicle ID");
            return;
        }
        Vehicle foundVehicle = find(vehicleID);
        System.out.println(foundVehicle.toString());
    }

}
