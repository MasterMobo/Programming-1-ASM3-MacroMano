package PortSystem.CLI;

import PortSystem.DB.MasterDatabase;
import PortSystem.Utils.DisplayUtils;

public class ListContainerFromVehicleCommand extends Command{
    public ListContainerFromVehicleCommand() {
        signature = "lscv";
        desc = "List containers (of given type) of a vehicle. Type 'all' for <containerType> to get all types";
        usage = "lscv <containerType> <vehicleId>";
        arguments = 2;
    }

    public static void process(String[] args, MasterDatabase db) {
        ListContainerFromVehicleCommand cmd = new ListContainerFromVehicleCommand();
        if (!cmd.validateArguments(args)) {
            return;
        }

        cmd.execute(args, db);
    }

    public void execute(String[] args, MasterDatabase db) {
        String type = args[0];
        String vehicleId = args[1];
        switch (type) {
            case "all":
                DisplayUtils.printArray(db.containers.fromVehicle(vehicleId));
                break;
            case "liquid":
                DisplayUtils.printArray(db.containers.fromVehicle(vehicleId, "Liquid"));
                break;
            case "dry":
                DisplayUtils.printArray(db.containers.fromVehicle(vehicleId, "Dry Storage"));
                break;
            case "openside":
                DisplayUtils.printArray(db.containers.fromVehicle(vehicleId, "Open Side"));
                break;
            case "opentop":
                DisplayUtils.printArray(db.containers.fromVehicle(vehicleId, "Open Top"));
                break;
            case "refridg":
                DisplayUtils.printArray(db.containers.fromVehicle(vehicleId,"Refrigerated"));
                break;
            default:
                DisplayUtils.printInvalidTypeError("all, liquid, dry, openside, opentop, refridg");
        }
    }
}
