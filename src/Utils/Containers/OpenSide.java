package Utils.Containers;

public class OpenSide extends Container {
    private static float shipFuelConsumption = 2.7F;
    private static float truckFuelConsumption = 3.2F;
    public OpenSide(String id, double weight, float shipFuelConsumption, float truckFuelConsumption) {
        super(id, weight, truckFuelConsumption, truckFuelConsumption);
    }
}