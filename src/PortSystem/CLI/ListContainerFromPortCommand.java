package PortSystem.CLI;

import PortSystem.DB.MasterDatabase;
import PortSystem.Utils.DisplayUtils;

public class ListContainerFromPortCommand extends Command{
    public ListContainerFromPortCommand() {
        signature = "lscp";
        desc = "List containers (of given type) of a port. Type 'all' for <containerType> to get all types";
        usage = "lscp <containerType> <portId>";
        arguments = 2;
    }

    public static void process(String[] args, MasterDatabase db) {
        ListContainerFromPortCommand cmd = new ListContainerFromPortCommand();
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
                DisplayUtils.printArray(db.containers.fromPort(vehicleId));
                break;
            case "liquid":
                DisplayUtils.printArray(db.containers.fromPort(vehicleId, "Liquid"));
                break;
            case "dry":
                DisplayUtils.printArray(db.containers.fromPort(vehicleId, "Dry Storage"));
                break;
            case "openside":
                DisplayUtils.printArray(db.containers.fromPort(vehicleId, "Open Side"));
                break;
            case "opentop":
                DisplayUtils.printArray(db.containers.fromPort(vehicleId, "Open Top"));
                break;
            case "refridg":
                DisplayUtils.printArray(db.containers.fromPort(vehicleId,"Refrigerated"));
                break;
            default:
                DisplayUtils.printInvalidTypeError("all, liquid, dry, openside, opentop, refridg");
        }
    }
}
