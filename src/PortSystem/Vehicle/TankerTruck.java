package PortSystem.Vehicle;

public class TankerTruck extends Truck {
    public TankerTruck(String name, Double carryCapacity, Double fuelCapacity) {
        super(name,
                carryCapacity,
                fuelCapacity);
        this.type = "Tanker Truck";
        this.allowedContainers = new String[]{"Dry Storage", "Liquid", "Open Side", "Open Top"};
    }

}