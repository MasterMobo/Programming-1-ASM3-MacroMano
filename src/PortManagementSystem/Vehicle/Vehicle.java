package PortManagementSystem.Vehicle;
import PortManagementSystem.Port;
import PortManagementSystem.Utils.*;
import PortManagementSystem.Containers.*;

import java.lang.foreign.StructLayout;
import java.util.*;

public class Vehicle implements VehicleOperation {

    private final String name;
    protected final String id;
    private Port port;
    public String portId;
    private Double carryCapacity;
    private Double curfuelCapacity;
    private final Double fuelCapacity;
    public ArrayList<Container> loadedContainers = new ArrayList<>();

    public Vehicle(String name, String id, Port port, Double carryCapacity, Double fuelCapacity) {
        this.name = name;
        this.id = id;
        this.port = port;
        this.portId = port.getId();
        this.carryCapacity = carryCapacity;
        this.curfuelCapacity = fuelCapacity;
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

            if (this instanceof Ship) {
                loadedContainers.add(container);
            } else if (this instanceof Truck) {
                if (container.getType() == "Refridgerated" && (this instanceof Truck.ReeferTruck)) {
                    System.out.println("Only Reefer truck can carry Refridgerated");
                } else if (container.getType() == "Liquid" && !(this instanceof Truck.TankerTruck)) {
                    System.out.println("Only Tanker truck can carry Liquid");
                } else {
                    loadedContainers.add(container);
                }
            }
            scanner.close();
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
}

