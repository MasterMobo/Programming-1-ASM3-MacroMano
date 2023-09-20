package PortSystem.DB;

import PortSystem.Containers.Container;
import PortSystem.Port.Port;
import PortSystem.Trip.Trip;
import PortSystem.Trip.TripStatus;
import PortSystem.Utils.*;
import PortSystem.Vehicle.Ship;
import PortSystem.Vehicle.Truck;
import PortSystem.Vehicle.Vehicle;

import java.time.LocalDate;
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
    private boolean tripExists(String tripId) {
        return mdb.trips.find(tripId) != null;
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



    public Float totalConsumption(String vehicleId, String nextPortId) {
        if (!(vehicleExists(vehicleId))) return null;
        Vehicle v = mdb.vehicles.find(vehicleId);

        if (!(portExists(nextPortId))) {
            return null;
        }


        Port currentPort = mdb.ports.find(v.portId);
        Port nextPort = mdb.ports.find(nextPortId);

        ArrayList<Container> loadedContainers = mdb.containers.fromVehicle(vehicleId);
        return v.calculateTotalConsumption(currentPort, nextPort, loadedContainers);
    }

    public void startMove(String vehicleId, String nextPortId, String departDate) {
        if (!(vehicleExists(vehicleId))) {
            return;
        }
        Vehicle v = mdb.vehicles.find(vehicleId);
        Port vCurrentPort = mdb.ports.find(v.portId);
        if (!(portExists(nextPortId))) {
            return;
        }
        Port nextPort = mdb.ports.find(nextPortId);
        Double totalConsumption = Double.valueOf(mdb.vehicles.totalConsumption(vehicleId, nextPortId));
        if (!(totalConsumption < v.getCurfuelCapacity())) {
            System.out.println("Vehicle not allowed to move due to fuel capacity exceeding");
            return;
        }

        DisplayUtils.printSystemMessage("Moving " + v.getName() + " from " + vCurrentPort.getName() + " to " + nextPort.getName());
        DisplayUtils.printSystemMessage("Confirm trip? (y/n)");
        Scanner scanner = new Scanner(System.in);
        String prompt = scanner.nextLine().trim();

        if (prompt.equals("n")) {
            DisplayUtils.printErrorMessage("Trip Cancelled");
            return;
        }

        if (prompt.equals("y")) {
            LocalDate lD = DateUtils.toLocalDate(departDate);
            Trip trip = new Trip(vehicleId, v.portId, nextPortId, lD, null, vCurrentPort.getDist(nextPort), totalConsumption, TripStatus.PROCESSING);
            mdb.trips.add(trip);
            DisplayUtils.printSystemMessage("Added new trip: " + trip);
            return;
        }

        // TODO maybe throw exception if input is wrong?
//        throw new Exception("Invalid input, Expecting 'y' or 'n");
    }


    public void refuelVehicle(String vehicleId, String userPortId) {
        Vehicle vehicle = mdb.vehicles.find(vehicleId);
        if (vehicle == null) return;

        if (!Objects.equals(vehicle.portId, userPortId)) {
            DisplayUtils.printErrorMessage("You do not have permission to this vehicle because it is not in your port");
            return;
        }

        if (vehicle.portId == null) {
            System.out.println("Can't refuel because vehicle is not on a trip");
            return;
        }

        vehicle.setCurfuelCapacity(vehicle.getFuelCapacity());
        mdb.save();
    }

    @Override
    public Vehicle createRecord(Vehicle vehicle) {
        if (vehicle == null) return null;
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter port ID: ");
        Port p = mdb.ports.find(scanner.nextLine().trim());
        if (p == null) return null;

        vehicle.portId = p.getId();
        p.increaseVehicleCount();
        add(vehicle);
        DisplayUtils.printSystemMessage("Created Record: " + vehicle);
        return vehicle;
    }

    @Override
    public Vehicle updateRecord(String id) {
        Vehicle vehicle = super.updateRecord(id);
        if (vehicle == null) return null;

        Scanner scanner = new Scanner(System.in);

        vehicle.setName(getInputString("Name: ", vehicle.getName(), scanner));

        vehicle.setCarryCapacity(getInputDouble("Carry Weight Capacity: ", vehicle.getCarryCapacity(), scanner));

        vehicle.setCurCarryWeight(getInputDouble("Current Carry Weight: ", vehicle.getCurCarryWeight(), scanner));

        vehicle.setFuelCapacity(getInputDouble("Fuel Capacity: ", vehicle.getFuelCapacity(), scanner));

        vehicle.portId = getInputId("Port ID: ", vehicle.portId, scanner, mdb.ports);

        vehicle.setCurfuelCapacity(vehicle.getFuelCapacity());
        mdb.save();
        DisplayUtils.printSystemMessage("Updated record: " + vehicle);
        return vehicle;
    }

    @Override
    public Vehicle delete(String id) {
        Vehicle deletedVehicle = super.delete(id);

        if (mdb.ports.exists(deletedVehicle.portId)) {
            mdb.ports.find(deletedVehicle.portId).decreaseVehicleCount();
        }

        return deletedVehicle;
    }

// TODO do you even need this? just use find()

//    public void showInfo(String vehicleID) {
//        if (!vehicleExists(vehicleID)) {
//            System.out.println("Invalid Vehicle ID");
//            return;
//        }
//        Vehicle foundVehicle = find(vehicleID);
//        System.out.println(foundVehicle.toString());
//    }

}
