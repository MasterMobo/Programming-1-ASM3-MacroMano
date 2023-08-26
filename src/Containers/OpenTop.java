package Containers;

public class OpenTop extends Container {
    private static String type = "Open Top";
    private static float shipFuelConsumption = 2.8F;
    private static float truckFuelConsumption = 3.2F;
    public OpenTop(String id, double weight) {
        super(id,weight);
    }
}
