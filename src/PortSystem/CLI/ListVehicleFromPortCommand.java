package PortSystem.CLI;

import PortSystem.DB.MasterDatabase;
import PortSystem.Utils.DisplayUtils;

public class ListVehicleFromPortCommand extends Command{

    public ListVehicleFromPortCommand() {
        super();
        signature = "lsv";
        desc = "List all vehicle (of given type) from a port. Type 'all' for <vehicleType> to get all types";
        usage = "lsv <vehicleType> <portId>";
        arguments = 2;
    }

    @Override
    public void execute(String[] args, MasterDatabase db, CLI cli) {
        String type = args[0];
        String portId = args[1];

        switch (type) {
            case "all":
                DisplayUtils.printArray(db.getVehicles().fromPort(portId));
                break;
            case "truck":
                DisplayUtils.printArray(db.getVehicles().trucksFromPort(portId));
                break;
            case "ship":
                DisplayUtils.printArray(db.getVehicles().shipsFromPort(portId));
                break;
            default:
                DisplayUtils.printInvalidTypeError("all, truck, ship");
        }
    }
}
