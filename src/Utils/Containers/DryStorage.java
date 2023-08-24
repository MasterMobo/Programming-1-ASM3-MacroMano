package Utils.Containers;

public class DryStorage extends Container {
    private static float shipFuelConsumption = 3.5F;
    private static float truckFuelConsumption = 4.6F;
    public DryStorage(String id, double weight, float shipFuelConsumption, float truckFuelConsumption) {
        super(id, weight, truckFuelConsumption, truckFuelConsumption);
    }
}
