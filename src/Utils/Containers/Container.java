package Utils.Containers;
public class Container {
    private String id;
    private double weight;
    private float shipFuelConsumption;
    private float truckFuelConsumption;

    public Container(String id, double weight, float shipFuelConsumption, float truckFuelConsumption) {
        this.id = id;
        this.weight = weight;
        this.shipFuelConsumption = shipFuelConsumption;
        this.truckFuelConsumption = truckFuelConsumption;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public float getShipFuelConsumption() {
        return shipFuelConsumption;
    }

    public void setShipFuelConsumption(float shipFuelConsumption) {
        this.shipFuelConsumption = shipFuelConsumption;
    }

    public float getTruckFuelConsumption() {
        return truckFuelConsumption;
    }

    public void setTruckFuelConsumption(float truckFuelConsumption) {
        this.truckFuelConsumption = truckFuelConsumption;
    }

    @Override
    public String toString() {
        return "Container{" +
                "id='" + id + '\'' +
                ", weight=" + weight +
                ", shipFuelConsumption=" + shipFuelConsumption +
                ", truckFuelConsumption=" + truckFuelConsumption +
                '}';
    }
}
