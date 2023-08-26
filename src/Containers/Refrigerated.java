package Containers;

public class Refrigerated extends Container {
    private static String type = "Refrigerated";
    private static float shipFuelConsumption = 4.5F;
    private static float truckFuelConsumption = 5.4F;
    public Refrigerated(String id, double weight) {
        super(id,weight);
    }
}