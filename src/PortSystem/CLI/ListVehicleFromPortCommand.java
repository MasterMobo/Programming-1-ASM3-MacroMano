package PortSystem.CLI;

import PortSystem.DB.MasterDatabase;
import PortSystem.Utils.DisplayUtils;

public class ListVehicleFromPortCommand extends Command{

    public ListVehicleFromPortCommand() {
        signature = "lsv";
        desc = "List all vehicle (of given type) in a port. Type 'all' for <vehicleType> to get all types";
        usage = "lsv <vehicleType> <portId>";
        arguments = 2;
    }

    public static void process(String[] args, MasterDatabase db) {
        ListVehicleFromPortCommand cmd = new ListVehicleFromPortCommand();
        if (!cmd.validateArguments(args)) {
            return;
        }

        cmd.execute(args, db);
    }

    public void execute(String[] args, MasterDatabase db) {
        String type = args[0];
        String portId = args[1];

        switch (type) {
            case "all":
                DisplayUtils.printArray(db.vehicles.fromPort(portId));
                break;
            case "truck":
                DisplayUtils.printArray(db.vehicles.trucksFromPort(portId));
                break;
            case "ship":
                DisplayUtils.printArray(db.vehicles.shipsFromPort(portId));
                break;
            default:
                DisplayUtils.printInvalidTypeError("all, truck, ship");
        }
    }
}
