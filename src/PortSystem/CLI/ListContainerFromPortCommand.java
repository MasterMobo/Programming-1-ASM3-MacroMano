package PortSystem.CLI;

import PortSystem.DB.MasterDatabase;

public class ListContainerFromPortCommand extends Command{
    public ListContainerFromPortCommand() {
        signature = "lscp";
        desc = "List containers (of given type) of a port";
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
                System.out.println(db.containers.fromPort(vehicleId));
                break;
            case "liquid":
                System.out.println(db.containers.fromPort(vehicleId, "Liquid"));
                break;
            case "dry":
                System.out.println(db.containers.fromPort(vehicleId, "Dry Storage"));
                break;
            case "openside":
                System.out.println(db.containers.fromPort(vehicleId, "Open Side"));
                break;
            case "opentop":
                System.out.println(db.containers.fromPort(vehicleId, "Open Top"));
                break;
            case "refridg":
                System.out.println(db.containers.fromPort(vehicleId,"Refrigerated"));
                break;
            default:
                System.out.println("Invalid Type. Expecting: all, liquid, dry, openside, opentop, refridg");
        }
    }
}
