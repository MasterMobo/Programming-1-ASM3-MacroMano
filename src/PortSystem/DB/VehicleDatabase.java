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
        return mdb.ports.exists(portId);
    }
    private boolean vehicleExists(String vehicleId) {
        return mdb.vehicles.exists(vehicleId);
    }
    private boolean tripExists(String tripId) {
        return mdb.trips.exists(tripId);
    }

    public ArrayList<Vehicle> fromPort(String portID) {
        if (!portExists(portID)) return null;

        ArrayList<Vehicle> res = new ArrayList<>();

        for (Map.Entry<String, Vehicle> set: data.entrySet()) {
            Vehicle vehicle = set.getValue();
            if (Objects.equals(vehicle.getPortId(), portID)) {
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
            if (Objects.equals(vehicle.getPortId(), portID) && vehicle instanceof Ship) {
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
            if (Objects.equals(vehicle.getPortId(), portID) && vehicle instanceof Truck) {
                res.add(vehicle);
            }
        }
        return res;
    }



    public double getTotalConsumption(Vehicle vehicle, Port currentPort, Port nextPort) {
        ArrayList<Container> loadedContainers = mdb.containers.fromVehicle(vehicle.getId());
        return vehicle.calculateTotalConsumption(currentPort, nextPort, loadedContainers);
    }

    public void startMove(String vehicleId, String nextPortId, String departDateString) {
        Vehicle v = mdb.vehicles.find(vehicleId);
        if (v  == null) return;

        if (v.getPortId() == null) {
            DisplayUtils.printErrorMessage("Vehicle is already on a trip");
        }

        Port vCurrentPort = mdb.ports.find(v.getPortId());
        if (vCurrentPort == null) return;

        Port nextPort = mdb.ports.find(nextPortId);
        if (nextPort == null) return;

        LocalDate departDate = DateUtils.toLocalDate(departDateString);
        if (departDate == null) return;

        // TODO also need these conditions for moving:
        //  1. Only the ports that are marked “landing” can utilize trucks for carrying.
        //  2. Check if vehicle is already on a different trip (is any trip associated with this vehicle still PROCESSING or EN_ROUTE?)
        //  3. Is vehicle already in the nextPort?
        //  also, can you pls put all the move conditions into one function instead of putting them here? (its really hard to read)

        double totalConsumption = getTotalConsumption(v, vCurrentPort, nextPort);
        if (!(totalConsumption < v.getCurfuelCapacity())) {
            DisplayUtils.printErrorMessage("Vehicle not allowed to move due to fuel capacity exceeding");
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
            Trip trip = new Trip(vehicleId, v.getPortId(), nextPortId, departDate, null, vCurrentPort.getDist(nextPort), totalConsumption, TripStatus.PROCESSING);
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

        if (userPortId != null && !Objects.equals(vehicle.getPortId(), userPortId)) {
            DisplayUtils.printErrorMessage("You do not have permission to this vehicle because it is not in your port");
            return;
        }

        if (vehicle.getPortId() == null) {
            DisplayUtils.printErrorMessage("Can't refuel because vehicle is on a trip");
            return;
        }

        vehicle.setCurfuelCapacity(vehicle.getFuelCapacity());
        DisplayUtils.printSystemMessage("Successfully refueled vehicle");
        mdb.save();
    }

    @Override
    public Vehicle createRecord(Vehicle vehicle) {
        if (vehicle == null) return null;
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter port ID: ");
        Port p = mdb.ports.find(scanner.nextLine().trim());
        if (p == null) return null;

        vehicle.setPortId(p.getId());
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

        vehicle.setPortId(getInputId("Port ID: ", vehicle.getPortId(), scanner, mdb.ports));

        vehicle.setCurfuelCapacity(vehicle.getFuelCapacity());
        mdb.save();
        DisplayUtils.printSystemMessage("Updated record: " + vehicle);
        return vehicle;
    }

    @Override
    public Vehicle delete(String id) {
        Vehicle deletedVehicle = super.delete(id);
        if (deletedVehicle == null) return null;

        if (mdb.ports.exists(deletedVehicle.getPortId())) {
            mdb.ports.find(deletedVehicle.getPortId()).decreaseVehicleCount();
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
