package Containers;

public class Refridgerated extends Container {
    private static String type = "Refrigerated";
    public static float shipFuelConsumption = 4.5F;
    public static float truckFuelConsumption = 5.4F;
    public Refridgerated(String id, double weight) {
        super(id,weight);
    }
}