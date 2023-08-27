import java.util.ArrayList;

import static java.lang.Math.*;

import Containers.*;
import Vehicle.*;

public class Port {
    private String name;
    private String id;
    private double lat;
    private double lon;
    private double capacity;
    private double currentWeight;
    private boolean isLanding;
    private ArrayList<Container> containers;
    private ArrayList<Vehicle> vehicles;

    public Port() {
    }

    public Port(String name, String id, double lat, double lon, double capacity, boolean isLanding) {
        this.name = name;
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.capacity = capacity;
        this.isLanding = isLanding;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public double getCapacity() {
        return capacity;
    }

    public double getCurrentWeight() {
        return currentWeight;
    }

    public boolean isLanding() {
        return isLanding;
    }

    public ArrayList<Container> getContainers() {
        return containers;
    }

    public ArrayList<Vehicle> getVehicles() {
        return vehicles;
    }

    public double getDist(Port other) {
        // Calculates the distance between this port and the other port (in km)
        return acos(sin(this.lat)*sin(other.lat)+cos(this.lat)*cos(other.lat)*cos(other.lon-this.lon))*6371; // (6371 is Earth radius in km.)
    }

    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
    }

    public void removeVehicle(Vehicle vehicle) {
        vehicles.remove(vehicle);
    }

    public void addContainer(Container container) {
        double containerWeight = container.getWeight();
        if (currentWeight + containerWeight > capacity) {
            System.out.println("Port capacity exceeded");
            return;
        }

        containers.add(container);
        currentWeight += containerWeight;
    }

    public void removeContainer(Container container) {
        containers.remove(container);
        currentWeight -= container.getWeight();
    }

    public ArrayList<Vehicle> getShips() {
        ArrayList<Vehicle> res = new ArrayList<>();
        for (Vehicle vehicle: vehicles) {
            if (vehicle instanceof Ship) res.add(vehicle);
        }
        return res;
    }

    public ArrayList<Vehicle> getTrucks() {
        ArrayList<Vehicle> res = new ArrayList<>();
        for (Vehicle vehicle: vehicles) {
            if (vehicle instanceof Truck) res.add(vehicle);
        }
        return res;
    }
}
