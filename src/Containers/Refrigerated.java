package Utils.Containers;

public class Refrigerated extends Container {
    private static float shipFuelConsumption = 4.5F;
    private static float truckFuelConsumption = 5.4F;
    public Refrigerated(String id, double weight, float shipFuelConsumption, float truckFuelConsumption) {
        super(id, weight, truckFuelConsumption, truckFuelConsumption);
    }
}