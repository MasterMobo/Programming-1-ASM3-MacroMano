package PortSystem.Vehicle;
import PortSystem.DB.DatabaseRecord;
import PortSystem.Port.Port;
import PortSystem.Containers.*;

import java.io.Serializable;
import java.util.*;

public class Vehicle implements DatabaseRecord, Serializable {

    private String name;

    protected String id;

    protected String type;
    public String portId;
    private double carryCapacity;

    private double curCarryWeight;
    private double fuelCapacity;
    private double curfuelCapacity = fuelCapacity;

    protected String[] allowedContainers;

    private double curFuelConsumption = 0.0;

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

    public void refuel() {
        this.curfuelCapacity = this.fuelCapacity;
    }

    public boolean canAddContainer(Container c){
        return getCurCarryWeight() + c.getWeight() <= getCarryCapacity();
    }

    public void addWeight(Container c){
        curCarryWeight += c.getWeight();
    }

    public void deductWeight(Container c){
        curCarryWeight -= c.getWeight();
    }
    public void deductFuel(Vehicle v) {
        v.getFuelCapacity() -= v.getCurfuelCapacity();
    };


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
        System.out.println("Enter vehicle type (ship, truck, reefer, tanker):");
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
            case "truck":
                return new Truck(name, carryCapacity, fuelCapacity);
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
