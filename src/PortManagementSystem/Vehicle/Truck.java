package PortManagementSystem.Vehicle;
import PortManagementSystem.*;
import PortManagementSystem.DB.DatabaseRecord;

public class Truck extends Vehicle {
    public Truck(String name, String id, Port port, Double carryCapacity, Double fuelCapacity) {
        super(name,
                id,
                port,
                carryCapacity,
                fuelCapacity);
    }
}