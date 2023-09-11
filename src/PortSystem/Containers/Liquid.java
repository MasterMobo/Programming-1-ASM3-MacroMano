package PortSystem.Containers;

public class Liquid extends Container {
    public Liquid(double weight) {
        super(weight);
        type = "Liquid";
        shipFuelConsumption = 4.8F;
        truckFuelConsumption = 5.3F;
    }
}
