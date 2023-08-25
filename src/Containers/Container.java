package Containers;
//Parent class
public abstract class Container {
    private String id;
    private double weight;
    private float shipFuelConsumption;
    private float truckFuelConsumption;
    private String type;

    public Container(String id, double weight, float shipFuelConsumption, float truckFuelConsumption, String type) {
        this.id = id;
        this.weight = weight;
        this.shipFuelConsumption = shipFuelConsumption;
        this.truckFuelConsumption = truckFuelConsumption;
        this.type = type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}


