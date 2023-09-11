package PortSystem.Vehicle;

public class Ship extends Vehicle {
    public Ship(String name, double carryCapacity, double fuelCapacity) {
        super(name,
                carryCapacity,
                fuelCapacity);
        this.type = "Ship";
        this.allowedContainers = new String[]{"Dry Storage", "Liquid", "Open Side", "Open Top", "Refrigerated"};
    }

}