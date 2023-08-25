package Utils.Containers;

public class Liquid extends Container {
    private static float shipFuelConsumption = 4.8F;
    private static float truckFuelConsumption = 5.3F;
    public Liquid(String id, double weight, float shipFuelConsumption, float truckFuelConsumption) {
        super(id, weight, truckFuelConsumption, truckFuelConsumption);
    }
}
