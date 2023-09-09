package PortManagementSystem.Containers;

public class OpenTop extends Container {
    public OpenTop(double weight) {
        super(weight);
        type = "Open Top";
        shipFuelConsumption = 2.8F;
        truckFuelConsumption = 3.2F;
    }
}
