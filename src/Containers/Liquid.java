package Containers;

public class Liquid extends Container {
    private static String type = "Liquid";
    public static float shipFuelConsumption = 4.8F;
    public static float truckFuelConsumption = 5.3F;
    public Liquid(String id, double weight) {
        super(id,weight);
    }
}
