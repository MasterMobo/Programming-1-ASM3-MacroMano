package Containers;

public class OpenSide extends Container {
    private static String type = "Open Side";
    public static float shipFuelConsumption = 2.7F;
    public static float truckFuelConsumption = 3.2F;
    public OpenSide(String id, double weight) {
        super(id,weight);
    }
}