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
        return mdb.getPorts().exists(portId);
    }
    private boolean vehicleExists(String vehicleId) {
        return mdb.getVehicles().exists(vehicleId);
    }
    private boolean tripExists(String tripId) {
        return mdb.getTrips().exists(tripId);
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
        ArrayList<Container> loadedContainers = mdb.getContainers().fromVehicle(vehicle.getId());
        return vehicle.calculateTotalConsumption(currentPort, nextPort, loadedContainers);
    }


    public void newTrip(String vehicleId, String nextPortId, String departDateString) {
        Vehicle v = mdb.getVehicles().find(vehicleId);
        if (v  == null) return;

        if (v.getPortId () == nextPortId) {
            System.out.println("This vehicle is already in this port");
            return;
        }

        for (Trip trip: mdb.getTrips().fromVehicle(vehicleId)) {
            if (trip.getStatus() == TripStatus.EN_ROUTE || trip.getStatus() == TripStatus.PROCESSING) {
                DisplayUtils.printErrorMessage("This vehicle is currently on a trip. Unable to receive a new trip");
                return;
            }
        }


        Port vCurrentPort = mdb.getPorts().find(v.getPortId());

        if (vCurrentPort == null) return;

        Port nextPort = mdb.getPorts().find(nextPortId);
        if (nextPort == null) return;

        LocalDate departDate = DateUtils.toLocalDate(departDateString);
        if (departDate == null) return;

        if ((!vCurrentPort.isLanding() || !nextPort.isLanding()) && v instanceof Truck) {
            DisplayUtils.printErrorMessage("The ports are currently unavailable for trucks");
            return;
        }



        // TODO also need these conditions for moving:
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
            mdb.getTrips().add(trip);

            DisplayUtils.printSystemMessage("Added new trip: " + trip);
            return;
        }
        DisplayUtils.printErrorMessage("Invalid input, Expecting 'y' or 'n\'");
    }


    public void refuelVehicle(String vehicleId, String userPortId) {
        Vehicle vehicle = mdb.getVehicles().find(vehicleId);
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
        Port p = mdb.getPorts().find(scanner.nextLine().trim());
        if (p == null) return null;

        vehicle.setPortId(p.getId());
        add(vehicle);
        DisplayUtils.printSystemMessage("Created Record: " + vehicle);
        return vehicle;
    }

    @Override
    public Vehicle updateRecord(String id) {
        Vehicle vehicle = super.updateRecord(id);
        if (vehicle == null) return null;

        Scanner scanner = new Scanner(System.in);

        vehicle.setName(DBUtils.getInputString("Name: ", vehicle.getName(), scanner));

        vehicle.setCarryCapacity(DBUtils.getInputDouble("Carry Weight Capacity: ", vehicle.getCarryCapacity(), scanner));

        vehicle.setCurCarryWeight(DBUtils.getInputDouble("Current Carry Weight: ", vehicle.getCurCarryWeight(), scanner));

        vehicle.setFuelCapacity(DBUtils.getInputDouble("Fuel Capacity: ", vehicle.getFuelCapacity(), scanner));

        vehicle.setPortId(DBUtils.getInputId("Port ID: ", vehicle.getPortId(), scanner, mdb.getPorts()));


        vehicle.setCurfuelCapacity(vehicle.getFuelCapacity());
        mdb.save();
        DisplayUtils.printSystemMessage("Updated record: " + vehicle);
        return vehicle;
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
