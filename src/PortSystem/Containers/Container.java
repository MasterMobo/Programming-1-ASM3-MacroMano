package PortSystem.Containers;

import PortSystem.DB.DatabaseRecord;

import java.io.Serializable;
import java.util.Scanner;

//Parent class
public abstract class Container implements DatabaseRecord, Serializable {
    protected String type;
    protected String id;
    public String vehicleId;
    public String portId;
    protected double weight;
    protected double shipFuelConsumption;
    protected double truckFuelConsumption;

    public Container(double weight) {
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

    public void setShipFuelConsumption(double shipFuelConsumption) {
        this.shipFuelConsumption = shipFuelConsumption;
    }

    public void setTruckFuelConsumption(double truckFuelConsumption) {
        this.truckFuelConsumption = truckFuelConsumption;
    }

    public double getShipFuelConsumption() {
        return shipFuelConsumption;
    }

    public double getTruckFuelConsumption() {
        return truckFuelConsumption;
    }

    public String getType() {
        return type;
    }

    public static Container createContainer() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter type (dry, liquid, openside, opentop, refridg)");
        String type = scanner.nextLine().trim();

        System.out.print("Enter weight: ");
        double weight = scanner.nextDouble();

        switch (type){
            case "dry":
                return new DryStorage(weight);
            case "liquid":
                return new Liquid(weight);
            case "openside":
                return new OpenSide(weight);
            case "refridg":
                return new Refrigerated(weight);
            default:
                System.out.println("Invalid type");
                return null;
        }
    }

    @Override
    public String toString() {
        return "Container{" +
                "type='" + type + '\'' +
                ", id='" + id + '\'' +
                ", vehicleId='" + vehicleId + '\'' +
                ", portId='" + portId + '\'' +
                ", weight=" + weight +
                ", shipFuelConsumption=" + shipFuelConsumption +
                ", truckFuelConsumption=" + truckFuelConsumption +
                '}';
    }

}


