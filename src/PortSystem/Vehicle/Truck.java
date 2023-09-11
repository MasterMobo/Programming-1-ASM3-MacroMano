package PortSystem.Vehicle;

public class Truck extends Vehicle {
    public Truck(String name, double carryCapacity, double fuelCapacity) {
        super(name,
                carryCapacity,
                fuelCapacity);
        this.allowedContainers = new String[]{"Dry Storage", "Open Side", "Open Top"};
    }
}