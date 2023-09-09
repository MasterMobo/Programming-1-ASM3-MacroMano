package PortManagementSystem.Vehicle;

import PortManagementSystem.Port;
public class TankerTruck extends Truck {
    public TankerTruck(String name, String id, Port port, Double carryCapacity, Double fuelCapacity) {
        super(name,
                id,
                port,
                carryCapacity,
                fuelCapacity);
    }
}