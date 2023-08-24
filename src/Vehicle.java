public class Vehicle {

    private final String name;
    protected final String id;
    private Port portId;
    private final Double carryCapacity;
    private final Double fuelCapacity;

//    private ArrayList<Containers> containerList;


//   added another param called currentCapacity in case when:
//      vehicle at port, either empty or is b
    public Vehicle(String name, String id, Port portId, Double carryCapacity, Double fuelCapacity) {
        this.name = name;
        this.id = id;
        this.portId = portId;
        this.carryCapacity = carryCapacity;
        this.fuelCapacity = fuelCapacity;
    }

    public static class Truck extends Vehicle{
        public Truck(String name, String id, Port portId, Double carryCapacity, Double fuelCapacity) {
            super(name,
                    id,
                    portId,
                    carryCapacity,
                    fuelCapacity);
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

    public static class Ship extends Vehicle {
        public Ship(String name, String id, Port portId, Double carryCapacity, Double fuelCapacity) {
            super(name,
                    id,
                    portId,
                    carryCapacity,
                    fuelCapacity);
        }
    }

    public static void refuel() {

    }
}
