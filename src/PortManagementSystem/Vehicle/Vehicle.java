package PortManagementSystem.Vehicle;
import PortManagementSystem.DB.DatabaseRecord;
import PortManagementSystem.Port;
import PortManagementSystem.Utils.*;
import PortManagementSystem.Containers.*;

import java.io.Serializable;
import java.util.*;

public class Vehicle implements VehicleOperation, DatabaseRecord, Serializable {

    private final String name;
    protected String id;
    public String portId;
    private Double carryCapacity;
    private Double curfuelCapacity;
    private final Double fuelCapacity;
    public ArrayList<Container> loadedContainers = new ArrayList<>();

    public Port port;

    public Vehicle(String name, Double carryCapacity, Double fuelCapacity) {
        this.name = name;
        this.carryCapacity = carryCapacity;
        curfuelCapacity = fuelCapacity;
        this.fuelCapacity = fuelCapacity;
    }


    // TODO: add logic for when actual consumption exceeded Capacity (PortManagementSystem.Trip.PortManagementSystem.Trip length)
    @Override
    public boolean allowToTravel() {
        boolean canTravel = true;

        if (this.getTotalWeight() > this.carryCapacity) {
            System.out.println("Loading Capacity Exceeded. Please unload.");
            canTravel = false;
        }

        if (this.totalConsumption() > this.fuelCapacity) {
            System.out.println("Fuel Capacity Exceeded. Please refuel.");
            canTravel = false;
        }
        return canTravel;
    }

    @Override
    public void moveToPort() {
        if (!this.allowToTravel()) {
            return;
        }
        this.curfuelCapacity -= this.totalConsumption();
    }

    @Override
    public void refuel() {
        this.curfuelCapacity = this.fuelCapacity;
    }

    @Override
    public void unloadContainer() {
        this.loadedContainers.clear();
    }

    @Override
    public void loadContainer() {
        Scanner scanner = new Scanner(System.in);
        Double totalWeight = 0.0;
        while (true) {
            System.out.print("Enter container ID (or 'exit' to stop): ");
            String containerId = scanner.nextLine();

            if (containerId.equalsIgnoreCase("exit")) {
                break;
            }

            Container container = getContainerObject(containerId);
            if (container == null) {
                System.out.println("No container object associated with the provided ID.");
                continue;
            }

            if (totalWeight > this.carryCapacity ) {
                break;
            }
            else {
                if (this instanceof Ship) {
                    loadedContainers.add(container);
                } else if (this instanceof Truck) {
                    if (Objects.equals(container.getType(), "Refridgerated") && (this instanceof ReeferTruck)) {
                        System.out.println("Only Reefer truck can carry Refridgerated");
                    } else if (Objects.equals(container.getType(), "Liquid") && !(this instanceof TankerTruck)) {
                        System.out.println("Only Tanker truck can carry Liquid");
                    } else {
                        loadedContainers.add(container);
                    }
                }
            }
        }
    }

        public Container getContainerObject (String containerID){
            for (Container container : port.getContainers()) {
                if (container.getId().equals(containerID)) {
                    return container;
                }
            }
            return null;
        }


//    UPDATED: Using 1D array to do calculation
        public double getTotalWeight () {
            double totalWeight = 0;
            for (Container container : loadedContainers) {
                totalWeight += container.getWeight();
            }
            return totalWeight;
        }
        public float totalConsumption () {
            float totalFuelConsumption = 0.0F;
            double portDistance = port.getDist(null);

            if (this instanceof Ship) {
                for (Container container : loadedContainers) {
                    totalFuelConsumption += container.getShipFuelConsumption() * container.getWeight() * portDistance;
                }

            } else if (this instanceof Truck) {
                for (Container container : loadedContainers) {
                    totalFuelConsumption += container.getTruckFuelConsumption() * container.getWeight() * portDistance;
                }
            }
            return totalFuelConsumption;
        }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public static Vehicle createVehicle() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Creating vehicle...");
        System.out.println("Enter vehicle type (ship, reefer, tanker):");
        String type = scanner.nextLine().trim();

        System.out.print("Enter name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter carry capacity: ");
        double carryCapacity = scanner.nextDouble();

        System.out.print("Enter fuel capacity: ");
        double fuelCapacity = scanner.nextDouble();

        switch (type){
            case "ship":
                return new Ship(name, carryCapacity, fuelCapacity);
            case "tanker":
                return new TankerTruck(name, carryCapacity, fuelCapacity);
            case "reefer":
                return new ReeferTruck(name, carryCapacity, fuelCapacity);
            default:
                System.out.println("Invalid vehicle type");
                return null;
        }
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", port=" + port +
                ", portId='" + portId + '\'' +
                ", carryCapacity=" + carryCapacity +
                ", curfuelCapacity=" + curfuelCapacity +
                ", fuelCapacity=" + fuelCapacity +
                '}';
    }
}