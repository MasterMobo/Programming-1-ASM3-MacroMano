package PortManagementSystem.Vehicle;
import PortManagementSystem.Port;
import PortManagementSystem.Utils.*;
import PortManagementSystem.Containers.*;
import java.util.*;

public class Vehicle implements VehicleOperation {

    private final String name;
    protected final String id;
    private Port port;
    public String portId;
    private final Double carryCapacity;
    private final Float fuelCapacity;
    public ArrayList<Container> loadedContainers = new ArrayList<>();
    private int totalContainerCount = 0;

    public Vehicle(String name, String id, Port port, Double carryCapacity, Float fuelCapacity) {
        this.name = name;
        this.id = id;
        this.port = port;
        this.portId = port.getId();
        this.carryCapacity = carryCapacity;
        this.fuelCapacity = fuelCapacity;
    }

// TODO: add logic for when actual consumption exceeded Capacity (PortManagementSystem.Trip.PortManagementSystem.Trip length)
    public void allowToTravel() {
        if (this.carryCapacity < this.totalContainerCount) {
            System.out.println("Loading Capacity Exceeded please unload");
        }
//        if (totalConsumption()) {

//        }
    }

//    TODO: add logic for special cases of truck, this method now adding every containers to all vehicle types
    @Override public void loadContainer() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
//            Implemented new ways to add container, adding by ID
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
            loadedContainers.add(container);
        }
        scanner.close();
    }

    public Container getContainerObject(String containerID) {
        for (Container container : portId.getContainers()) {
            if (container.getId().equals(containerID)) {
                return container;
            }
        }
        return null;
    }



//    UPDATED: Using 1D array to do calculation
    public boolean totalConsumption(Vehicle vehicle) {
        float totalFuelConsumption = 0.0F;
        double portDistance = port.getDist(null);

        if (vehicle instanceof Ship) {
            for (Container container : vehicle.loadedContainers) {
                totalFuelConsumption += container.getShipFuelConsumption() * container.getWeight() * portDistance;
            }

        } else if (vehicle instanceof Truck) {
            for (Container container : vehicle.loadedContainers) {
                totalFuelConsumption += container.getTruckFuelConsumption() * container.getWeight() * portDistance;
            }

        }

        if (totalFuelConsumption < vehicle.fuelCapacity) {
            return true;
        }
         return false;
    }



    private boolean isValidContainerType(String containerType) {
        List<String> validTypes = Arrays.asList("Dry Storage", "Open Top", "Open Side", "Refrigerated", "Liquid");
        return validTypes.contains(containerType);
    }

}
