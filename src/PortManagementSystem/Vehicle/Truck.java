package PortManagementSystem.Vehicle;
import
public class Truck extends Vehicle {
    public Truck(String name, String id, Port portId, Double carryCapacity, Double fuelCapacity) {
        super(name,
                id,
                portId,
                carryCapacity,
                fuelCapacity););
    }

    public static class ReeferTruck extends Truck {
        public ReeferTruck(String name, String id, Port portId, Double carryCapacity, Double fuelCapacity) {
            super(name,
                    id,
                    portId,
                    carryCapacity,
                    fuelCapacity);
        }
    }

    public static class TankerTruck extends Truck {
        public TankerTruck(String name, String id, Port portId, Double carryCapacity, Double fuelCapacity) {
            super(name,
                    id,
                    portId,
                    carryCapacity,
                    fuelCapacity);
        }
    }
}