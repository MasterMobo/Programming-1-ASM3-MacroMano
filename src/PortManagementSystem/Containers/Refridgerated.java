package PortManagementSystem.Containers;

public class Refridgerated extends Container {
    public Refridgerated(double weight) {
        super(weight);
        type = "Refrigerated";
        shipFuelConsumption = 4.5F;
        truckFuelConsumption = 5.4F;
    }
}