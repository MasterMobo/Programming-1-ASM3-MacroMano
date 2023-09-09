package PortManagementSystem.Containers;

public class OpenSide extends Container {
    public OpenSide(double weight) {
        super(weight);
        type = "Open Side";
        shipFuelConsumption = 2.7F;
        truckFuelConsumption = 3.2F;
    }
}