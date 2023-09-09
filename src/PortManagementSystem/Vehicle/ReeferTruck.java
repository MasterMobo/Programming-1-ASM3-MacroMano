package PortManagementSystem.Vehicle;

import PortManagementSystem.Port;

public class ReeferTruck extends Truck {
    public ReeferTruck(String name, String id, Port port, Double carryCapacity, Double fuelCapacity) {
        super(name,
                id,
                port,
                carryCapacity,
                fuelCapacity);
    }
}