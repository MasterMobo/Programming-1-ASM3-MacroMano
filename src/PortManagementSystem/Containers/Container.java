package PortManagementSystem.Containers;

import PortManagementSystem.DB.DatabaseRecord;

import java.io.Serializable;

//Parent class
public abstract class Container implements DatabaseRecord, Serializable {
    private String type;
    protected String id;
    public String vehicleId;
    protected double weight;
    private float shipFuelConsumption;
    private float truckFuelConsumption;

    public Container(String id, double weight) {
        this.id = id;
        this.weight = weight;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
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

    public float getTruckFuelConsumption() {
        return truckFuelConsumption;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Container{" + 
                ", type='" + type + '\'' +
                ", weight= " + weight + "kg" +
                ", shipFuelConsumption= " + shipFuelConsumption +
                ", truckFuelConsumption= " + truckFuelConsumption +
                "id='" + id + '\'' +
                '}';
    }

    public static void main(String[] args) {
        DryStorage c1 = new DryStorage("DR01", 50);
        System.out.println(c1);
    }
}


