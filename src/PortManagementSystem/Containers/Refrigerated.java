package PortManagementSystem.Containers;

public class Refrigerated extends Container {
    public Refrigerated(double weight) {
        super(weight);
        type = "Refrigerated";
        shipFuelConsumption = 4.5F;
        truckFuelConsumption = 5.4F;
    }
}