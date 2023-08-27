package Containers;

public class OpenTop extends Container {
    private static String type = "Open Top";
    public static float shipFuelConsumption = 2.8F;
    public static float truckFuelConsumption = 3.2F;
    public OpenTop(String id, double weight) {
        super(id,weight);
    }
}
