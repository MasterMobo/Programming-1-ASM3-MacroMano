package Vehicle;

public class Ship extends Vehicle {
    public Ship(String name, String id, Port portId, Double carryCapacity, Double fuelCapacity) {
        super(name,
                id,
                portId,
                carryCapacity,
                fuelCapacity);
    }
}