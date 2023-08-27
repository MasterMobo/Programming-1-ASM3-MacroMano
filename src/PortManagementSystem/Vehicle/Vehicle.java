package PortManagementSystem.Vehicle;
import PortManagementSystem.Port;
import PortManagementSystem.Utils.*;
import PortManagementSystem.Containers.*;
import java.util.*;

public class Vehicle implements VehicleOperation {

    private final String name;
    protected final String id;
    public Port portId;
    private final Double carryCapacity;
    private final Float fuelCapacity;
    private static Scanner scanner;
    public HashMap<String, Integer> typeCounts = new HashMap<>();
    public HashMap<String, Integer> totalTypeCounts = new HashMap<>();
    public HashSet<String> uniqueContainerIds = new HashSet<>();
    public ArrayList<Container> loadedContainers = new ArrayList<>();
    private int totalContainerCount = 0;

    public Vehicle(String name, String id, Port portId, Double carryCapacity, Double fuelCapacity) {
        this.name = name;
        this.id = id;
        this.portId = portId;
        this.carryCapacity = carryCapacity;
        this.fuelCapacity = fuelCapacity;
    }

// TODO: add logic for when actual consumption exceeded Capacity (PortManagementSystem.Trip.PortManagementSystem.Trip length)
    public void allowToTravel() {
        if (this.carryCapacity < this.totalContainerCount) {
            System.out.println("Loading Capacity Exceeded please unload");
        }
        if (totalConsumption()) {

        }
    }

//    TODO: add logic for special cases of truck, this method now adding every containers to all vehicle types
    @Override public void loadContainer() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
//            Taking PortManagementSystem.User input of the container type and to stop the loop
            System.out.print("Enter container type (or 'exit' to stop): ");
            String containerType = scanner.nextLine();

            if (containerType.equalsIgnoreCase("exit")) {
                break;
            }

            if (!isValidContainerType(containerType)) {
                System.out.println("Invalid container type. Please re-enter.");
                continue;
            }


            totalTypeCounts.put(containerType, totalTypeCounts.getOrDefault(containerType, 0) + 1);
//            Ask user for container ID
            System.out.print("Enter container ID: ");
            String containerId = scanner.nextLine();

            if (uniqueContainerIds.contains(containerId)) {
                System.out.println("Container ID already used. Please re-enter a unique ID.");
                continue;
            }

//            Note: So in here I wanna check if there is already existing container object with the input ID
            Container container = getContainerObject(containerId);
            if (container == null) {
                System.out.println("No container object associated with the provided ID.");
                continue;
            }

            loadedContainers.add(container);
            uniqueContainerIds.add(containerId);
//            Count the total of each type and total of all type
            totalTypeCounts.put(containerType, totalTypeCounts.getOrDefault(containerType, 0) + 1);
            totalContainerCount++;
        }

        scanner.close();
    }

    public void printTotalTypeCounts() {
        System.out.println("Total Type Counts:");
        for (Map.Entry<String, Integer> entry : totalTypeCounts.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    public int getTotalContainerCount() {
        return totalContainerCount;
    }

//    This class is used to calculate the fuel consumption of all containers type
    public boolean totalConsumption(Vehicle vehicle) {
        float totalFuelConsumption = 0.0F;
        if (vehicle instanceof Ship) {
            for (Map.Entry<String, Integer> entry : totalTypeCounts.entrySet()) {
                String containerType = entry.getKey();
                int totalCount = entry.getValue();
                float fuelConsumption = ContainerFuelUtils.getFuelConsumptionForType(containerType, "Ship");
                float typeTotalFuelConsumption = fuelConsumption * totalCount;
                totalFuelConsumption += typeTotalFuelConsumption;
            }

        } else if (vehicle instanceof Truck) {
            for (Map.Entry<String, Integer> entry : totalTypeCounts.entrySet()) {
                String containerType = entry.getKey();
                int totalCount = entry.getValue();
                float fuelConsumption = ContainerFuelUtils.getFuelConsumptionForType(containerType, "Truck");
                float typeTotalFuelConsumption = fuelConsumption * totalCount;
                totalFuelConsumption += typeTotalFuelConsumption;
            }

        }

        if (totalFuelConsumption < vehicle.fuelCapacity) {
            return true;
        }
        return false;
    }



    private boolean isValidContainerType(String containerType) {
        List<String> validTypes = Arrays.asList("Dry Storage", "Open Top", "Open Side", "Refridgerated", "Liquid");
        return validTypes.contains(containerType);
    }

}
