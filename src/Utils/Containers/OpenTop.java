package Utils.Containers;

public class OpenTop extends Container {
    private static float shipFuelConsumption = 2.8F;
    private static float truckFuelConsumption = 3.2F;
    public OpenTop(String id, double weight, float shipFuelConsumption, float truckFuelConsumption) {
        super(id, weight, truckFuelConsumption, truckFuelConsumption);
    }
}
