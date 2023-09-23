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
import java.util.*;


public class VehicleDatabase extends Database<Vehicle> {
    // Specialized class to store Vehicle records

    public VehicleDatabase(MasterDatabase mdb) {
        super(mdb, "v");
    }

    public ArrayList<Vehicle> fromPort(String portID) {
        // Returns all vehicles in a given port, null if no port found

        Port port = mdb.getPorts().find(portID);
        if (port == null) return null;

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
        // Returns all ships in a given port, null if no port found

        Port port = mdb.getPorts().find(portID);
        if (port == null) return null;

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
        // Returns all trucks in a given port, null if no port found

        Port port = mdb.getPorts().find(portID);
        if (port == null) return null;

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

    public int getTotalContainerCount(String vehicleId) {
        ArrayList<Container> containers = mdb.getContainers().fromVehicle(vehicleId);
        if (containers == null) return 0;

        return containers.size();
    }

    public int getTotalContainerCount(String vehicleId, String type) {
        ArrayList<Container> containers = mdb.getContainers().fromVehicle(vehicleId, type);
        if (containers == null) return 0;

        return containers.size();
    }

    public void newTrip(String vehicleId, String nextPortId, String departDateString) {
        Vehicle v = find(vehicleId);
        if (v  == null) return;

        if (Objects.equals(v.getPortId(), nextPortId)) {
            System.out.println("This vehicle is already in this port");
            return;
        }

        for (Trip trip: mdb.getTrips().fromVehicle(vehicleId)) {
            if (trip.getStatus() == TripStatus.EN_ROUTE || trip.getStatus() == TripStatus.PROCESSING) {
                DisplayUtils.printErrorMessage("This vehicle is currently on a trip. Unable to create a new trip");
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

        double totalConsumption = getTotalConsumption(v, vCurrentPort, nextPort);
        if (!(totalConsumption < v.getCurFuelAmount())) {
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

        DisplayUtils.printInvalidTypeError("y, n");
    }


    public void refuelVehicle(String vehicleId, String userPortId) {
        Vehicle vehicle = find(vehicleId);
        if (vehicle == null) return;

        if (userPortId != null && !Objects.equals(vehicle.getPortId(), userPortId)) {
            DisplayUtils.printErrorMessage("You do not have permission to this vehicle because it is not in your port");
            return;
        }

        if (vehicle.getPortId() == null) {
            DisplayUtils.printErrorMessage("Can't refuel because this vehicle is currently on a trip");
            return;
        }

        vehicle.refuel();
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

        vehicle.setCurFuelAmount(DBUtils.getInputDouble("Current Fuel Amount: ", vehicle.getCurFuelAmount(), scanner));

        vehicle.setPortId(DBUtils.getInputId("Port ID: ", vehicle.getPortId(), scanner, mdb.getPorts()));

        mdb.save();
        DisplayUtils.printSystemMessage("Updated record: " + vehicle);
        return vehicle;
    }

    @Override
    public void showInfo(String vehicleID) {
        Vehicle foundVehicle = find(vehicleID);
        if (foundVehicle == null) return;

        System.out.println(
                "Vehicle {" +
                "\n         Name: " + foundVehicle.getName() + ", " +
                "\n         ID: " + foundVehicle.getId() + ", " +
                "\n         Type: " + foundVehicle.getType() + ", " +
                "\n         Port ID: " + foundVehicle.getPortId() + ", " +
                "\n         Carry Capacity: " + DisplayUtils.formatDouble(foundVehicle.getCarryCapacity()) + ", Current Carry Weight: " + DisplayUtils.formatDouble(foundVehicle.getCurCarryWeight()) +
                "\n         Fuel Capacity: " + DisplayUtils.formatDouble(foundVehicle.getFuelCapacity()) + ", Current Fuel Amount: " + DisplayUtils.formatDouble(foundVehicle.getCurFuelAmount()) + ", " +
                "\n         Allowed Containers: " + Arrays.toString(foundVehicle.getAllowedContainers())  + ", " +
                "\n         Container Count: " + getTotalContainerCount(foundVehicle.getId()) + " (Dry Storage: " + getTotalContainerCount(foundVehicle.getId(), "Dry Storage") + "; Liquid: " + getTotalContainerCount(foundVehicle.getId(), "Liquid") + "; Open Storage: " + getTotalContainerCount(foundVehicle.getId(), "Open Storage") + "; Open Top: " + getTotalContainerCount(foundVehicle.getId(), "Open Top") + "; Refrigerated: " + getTotalContainerCount(foundVehicle.getId(), "Refrigerated") + ")" +
                "\n         }");
    }
}
