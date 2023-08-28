package PortManagementSystem.Vehicle;
import PortManagementSystem.*;
public class Ship extends Vehicle {
    public Ship(String name, String id, Port port, Double carryCapacity, Double fuelCapacity) {
        super(name,
                id,
                port,
                carryCapacity,
                fuelCapacity);
    }
}