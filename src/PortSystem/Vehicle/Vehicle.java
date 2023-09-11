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
    private double curFuelConsumption = 0;

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

    public double getCurFuelConsumption() {
        return curFuelConsumption;
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

    public void setCurFuelConsumption(double curFuelConsumption) {
        this.curFuelConsumption = curFuelConsumption;
    }

    public double getCurCarryWeight() {
        return curCarryWeight;
    }

    public double getCarryCapacity() {
        return carryCapacity;
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

    public boolean allowToAdd(Container c){
        for(String s: allowedContainers) {
            if (s.equals(c.getType())){
                return true;
            }
        }
        return false;
    };

    // TODO @alakinn pls use this method instead of totalConsumption
    //  curCarryWeight & curFuelConsumption is updated every time container is loaded/unloaded
    //  check ContainerDatabase.loadContainerOnVehicle for more detail
    public boolean canMove(Port p1, Port p2) {
        return curCarryWeight * curFuelConsumption * p1.getDist(p2) <= fuelCapacity;
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

    public boolean canAddContainer(Container c){
        return getCurCarryWeight() + c.getWeight() <= getCarryCapacity();
    }

    public void addWeight(Container c){
        curCarryWeight += c.getWeight();
    }

    public void addFuelConsumption(Container c){
        if (this instanceof Ship){
            curFuelConsumption += c.getShipFuelConsumption();
        }
        else if(this instanceof Truck){
            curFuelConsumption += c.getTruckFuelConsumption();
        }
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
        public double getTotalWeight () {
            double totalWeight = 0;
            for (Container container : loadedContainers) {
                totalWeight += container.getWeight();
            }
            return totalWeight;
        }
        public float totalConsumption () {
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
                ", type='" + type + '\'' +
                ", portId='" + portId + '\'' +
                ", carryCapacity=" + carryCapacity +
                ", curCarryWeight=" + curCarryWeight +
                ", fuelCapacity=" + fuelCapacity +
                ", curfuelCapacity=" + curfuelCapacity +
                ", allowedContainers=" + Arrays.toString(allowedContainers) +
                ", curFuelConsumption=" + curFuelConsumption +
                '}';
    }
}
