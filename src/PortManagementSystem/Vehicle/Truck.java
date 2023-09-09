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

    public static class ReeferTruck extends Truck {
        public ReeferTruck(String name, String id, Port port, Double carryCapacity, Double fuelCapacity) {
            super(name,
                    id,
                    port,
                    carryCapacity,
                    fuelCapacity);
        }
    }

    public static class TankerTruck extends Truck {
        public TankerTruck(String name, String id, Port port, Double carryCapacity, Double fuelCapacity) {
            super(name,
                    id,
                    port,
                    carryCapacity,
                    fuelCapacity);
        }
    }
}