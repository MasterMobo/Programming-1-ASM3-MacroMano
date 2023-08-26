package Containers;

public class DryStorage extends Container {
    private static String type = "Dry Storage";
    private static float shipFuelConsumption = 3.5F;
    private static float truckFuelConsumption = 4.6F;

    public DryStorage(String id, double weight) {
        super(id,weight);
    }
}
