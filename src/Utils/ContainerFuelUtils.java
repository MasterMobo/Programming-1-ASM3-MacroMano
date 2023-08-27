package Utils;
import Containers.*;
public class ContainerFuelUtils {
    public static float getFuelConsumptionForType(String containerType, String vehicleType) {
        switch (containerType) {
            case "Dry Storage":
                return getDryStorageFuelConsumption(vehicleType);
            case "Open Top":
                return getOpenTopFuelConsumption(vehicleType);
            case "Open Side":
                return getOpenTopFuelConsumption(vehicleType);
            case "Refridgerated":
                return getOpenTopFuelConsumption(vehicleType);
            case "Liquid":
                return getOpenTopFuelConsumption(vehicleType);

            default:
                return 0.0F;  // Default value if container type is unknown
        }
    }

    private static float getDryStorageFuelConsumption(String vehicleType) {
        if (vehicleType.equals("Ship")) {
            return DryStorage.shipFuelConsumption;
        } else if (vehicleType.equals("Truck")) {
            return DryStorage.truckFuelConsumption;
        } else {
            return 0.0F;  // Default value if vehicle type is unknown
        }
    }

    private static float getOpenTopFuelConsumption(String vehicleType) {
        if (vehicleType.equals("Ship")) {
            return OpenTop.shipFuelConsumption;
        } else if (vehicleType.equals("Truck")) {
            return OpenTop.truckFuelConsumption;
        } else {
            return 0.0F;  // Default value if vehicle type is unknown
        }
    }

    private static float getOpenSideFuelConsumption(String vehicleType) {
        if (vehicleType.equals("Ship")) {
            return OpenSide.shipFuelConsumption;
        } else if (vehicleType.equals("Truck")) {
            return OpenSide.truckFuelConsumption;
        } else {
            return 0.0F;  // Default value if vehicle type is unknown
        }
    }

    private static float getRefridgeratedFuelConsumption(String vehicleType) {
        if (vehicleType.equals("Ship")) {
            return Refridgerated.shipFuelConsumption;
        } else if (vehicleType.equals("Truck")) {
            return Refridgerated.truckFuelConsumption;
        } else {
            return 0.0F;  // Default value if vehicle type is unknown
        }
    }

    private static float getLiquidFuelConsumption(String vehicleType) {
        if (vehicleType.equals("Ship")) {
            return Liquid.shipFuelConsumption;
        } else if (vehicleType.equals("Truck")) {
            return Liquid.truckFuelConsumption;
        } else {
            return 0.0F;  // Default value if vehicle type is unknown
        }
    }



}
