package PortSystem.DB;

import PortSystem.Containers.Container;
import PortSystem.Port.Port;
import PortSystem.Trip.Trip;
import PortSystem.Trip.TripStatus;
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



    public Float totalConsumption(String vehicleId, String tripId) {
        Vehicle vehicle = mdb.vehicles.find(vehicleId);
        if (vehicle == null) return null;

        Trip trip = mdb.trips.find(tripId);
        if (trip == null) return null;

        if (!mdb.ports.exists(vehicle.portId)) {
            System.out.println("Vehicle's port can't be found");
            return null;
        }

        if(!(trip.vehicleId == vehicleId)) {
            System.out.println("This vehicle does not belong to this trip!");
            return null;
        }

        Port currentPort = mdb.ports.find(trip.departPortId);
        Port nextPort = mdb.ports.find(trip.arrivePortId);

        ArrayList<Container> loadedContainers = mdb.containers.fromVehicle(vehicleId);
        return vehicle.calculateTotalConsumption(currentPort, nextPort, loadedContainers);
    }


    public void move(String vehicleId) {
//        Checking if object exists
        Vehicle vehicle = mdb.vehicles.find(vehicleId);
        if (vehicle == null) return;

        Trip trip = mdb.trips.find(vehicleId);

        if (!mdb.ports.exists(vehicle.portId)) {   
            System.out.println("Vehicle's port can't be found");
            return;
        }

        if(!(trip.vehicleId == vehicleId)) {
            System.out.println("This vehicle does not belong to this trip!");
            return ;
        }
//        Default status for trip
        trip.setStatus(TripStatus.PROCESSING);
        System.out.println("Trip is being processed to check if vehicle is capable");
        Double totalConsumption = Double.valueOf(mdb.vehicles.totalConsumption(vehicleId, trip.arrivePortId));
        if (!(totalConsumption < vehicle.getCurfuelCapacity())) {
            System.out.println("Vehicle not allowed to move due to fuel capacity exceeding");
            return;
        }
        vehicle.setCurfuelCapacity(vehicle.getCurfuelCapacity() - totalConsumption);

//        Next Status
        trip.setStatus(TripStatus.EN_ROUTE);
        System.out.println("Vehicle is en route!");
        vehicle.portId = null;

//        Fulfilled Status
        trip.setStatus(TripStatus.FULFILLED);
        vehicle.portId = trip.arrivePortId;
        trip.setFuelConsumed(totalConsumption);

    }


    public void refuelVehicle(String vehicleId) {
        Vehicle vehicle = mdb.vehicles.find(vehicleId);
        if (vehicle == null) return;
        Trip trip = mdb.trips.find(vehicleId);

        if (trip.getStatus() == TripStatus.EN_ROUTE) {
            System.out.println("Can't pump what's can't pump");
            return;
        }

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
