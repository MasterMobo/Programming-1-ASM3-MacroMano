package PortSystem.Vehicle;
import PortSystem.DB.DatabaseRecord;
import PortSystem.Port.Port;
import PortSystem.Containers.*;

import java.io.Serializable;
import java.util.*;

public class Vehicle implements VehicleOperations, DatabaseRecord, Serializable {

    private String name;

    protected String id;

    protected String type;
    public String portId;
    private double carryCapacity;
    private double curfuelCapacity;
    private double curCarryWeight;
    private double fuelCapacity;

    protected String[] allowedContainers;
    private float totalFuelConsumption;

    // TODO pls do not use loadedContainers, use ContainerDatabase.fromVehicle instead
    public ArrayList<Container> loadedContainers = new ArrayList<>();

    public Port port;

    public Vehicle(String name, double carryCapacity, double fuelCapacity) {
        this.name = name;
        this.carryCapacity = carryCapacity;
        curfuelCapacity = fuelCapacity;
        this.fuelCapacity = fuelCapacity;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCurfuelCapacity() {
        return curfuelCapacity;
    }
    public float getTotalFuelConsumption() {return totalFuelConsumption;}

    public void setFuelCapacity(double fuelCapacity) {
        this.fuelCapacity = fuelCapacity;
    }

    public double getFuelCapacity() {
        return fuelCapacity;
    }


    public void setCarryCapacity(double carryCapacity) {
        this.carryCapacity = carryCapacity;
    }

    public void setCurfuelCapacity(double curfuelCapacity) {
        this.curfuelCapacity = curfuelCapacity;
    }

    public void setCurCarryWeight(double curCarryWeight) {
        this.curCarryWeight = curCarryWeight;
    }

    public double getCurCarryWeight() {
        return curCarryWeight;
    }

    public double getCarryCapacity() {
        return carryCapacity;
    }

    public boolean allowToAdd(Container c){
        for(String s: allowedContainers) {
            if (s.equals(c.getType())){
                return true;
            }
        }
        return false;
    };

    // TODO: add logic for when actual consumption exceeded Capacity (PortManagementSystem.Trip.PortManagementSystem.Trip length)
    @Override
    public boolean allowToTravel(float totalConsumption) {
        boolean canTravel = true;

        if (curCarryWeight > carryCapacity) {
            System.out.println("Loading Capacity Exceeded. Please unload.");
            canTravel = false;
        }

        if (totalConsumption > fuelCapacity) {
            System.out.println("Fuel Capacity Exceeded. Please refuel.");
            canTravel = false;
        }

        return canTravel;
    }


    @Override
    public void moveToPort(Port p1, Port p2, ArrayList<Container> containers) {
        float totalConsumption = calculateTotalConsumption(p1, p2, containers);
        if (!allowToTravel(totalConsumption)) {
            return;
        }
        curfuelCapacity -= totalConsumption;
    }

    @Override
    public void refuel() {
        this.curfuelCapacity = this.fuelCapacity;
    }

    public boolean canAddContainer(Container c){
        return getCurCarryWeight() + c.getWeight() <= getCarryCapacity();
    }

    public void addWeight(Container c){
        curCarryWeight += c.getWeight();
    }


    @Override
    public void unloadContainer() {
        this.loadedContainers.clear();
    }

//        public Container getContainerObject (String containerID){
//            for (Container container : port.getContainers()) {
//                if (container.getId().equals(containerID)) {
//                    return container;
//                }
//            }
//            return null;
//        }


//    UPDATED: Using 1D array to do calculation
    public double getTotalWeight() {
        double totalWeight = 0;
        for (Container container : loadedContainers) {
            totalWeight += container.getWeight();
        }
        return totalWeight;
    }

    public float calculateTotalConsumption(Port p1, Port p2, ArrayList<Container> containers) {
        float result = 0;
        double portDistance = p1.getDist(p2);

        for (Container container : containers) {
            if (this instanceof Ship) {
                result += container.getShipFuelConsumption() * container.getWeight() * portDistance;
            } else if (this instanceof Truck) {
                result += container.getTruckFuelConsumption() * container.getWeight() * portDistance;
            }
        }
        return result;
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
                ", type='" + type + '\'' +
                ", portId='" + portId + '\'' +
                ", carryCapacity=" + carryCapacity +
                ", curCarryWeight=" + curCarryWeight +
                ", fuelCapacity=" + fuelCapacity +
                ", curfuelCapacity=" + curfuelCapacity +
                ", allowedContainers=" + Arrays.toString(allowedContainers) +
                '}';
    }
}
