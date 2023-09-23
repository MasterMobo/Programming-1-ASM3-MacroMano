package PortSystem.CLI;

import PortSystem.DB.MasterDatabase;
import PortSystem.Utils.DisplayUtils;

public class ListContainerFromVehicleCommand extends Command{
    public ListContainerFromVehicleCommand() {
        super();
        signature = "lscv";
        desc = "List containers (of given type) from a vehicle. Type 'all' for <containerType> to get all types";
        usage = "lscv <containerType> <vehicleId>";
        arguments = 2;
        loginRequired = true;
    }

    @Override
    public void execute(String[] args, MasterDatabase db, CLI cli) {
        String type = args[0];
        String vehicleId = args[1];
        switch (type) {
            case "all":
                DisplayUtils.printArray(db.getContainers().fromVehicle(vehicleId));
                break;
            case "liquid":
                DisplayUtils.printArray(db.getContainers().fromVehicle(vehicleId, "Liquid"));
                break;
            case "dry":
                DisplayUtils.printArray(db.getContainers().fromVehicle(vehicleId, "Dry Storage"));
                break;
            case "openside":
                DisplayUtils.printArray(db.getContainers().fromVehicle(vehicleId, "Open Side"));
                break;
            case "opentop":
                DisplayUtils.printArray(db.getContainers().fromVehicle(vehicleId, "Open Top"));
                break;
            case "refridg":
                DisplayUtils.printArray(db.getContainers().fromVehicle(vehicleId,"Refrigerated"));
                break;
            default:
                DisplayUtils.printInvalidTypeError("all, liquid, dry, openside, opentop, refridg");
        }
    }
}
