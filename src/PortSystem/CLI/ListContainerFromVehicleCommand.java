package PortSystem.CLI;

import PortSystem.DB.MasterDatabase;

public class ListContainerFromVehicleCommand extends Command{
    public ListContainerFromVehicleCommand() {
        signature = "lsc";
        desc = "List containers (of given type) of a vehicle";
        usage = "lsc <containerType> <vehicleId>";
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
                System.out.println(db.containers.fromVehicle(vehicleId));
                break;
            case "liquid":
                System.out.println(db.containers.fromVehicle(vehicleId, "Liquid"));
                break;
            case "dry":
                System.out.println(db.containers.fromVehicle(vehicleId, "Dry Storage"));
                break;
            case "openside":
                System.out.println(db.containers.fromVehicle(vehicleId, "Open Side"));
                break;
            case "opentop":
                System.out.println(db.containers.fromVehicle(vehicleId, "Open Top"));
            case "refridg":
                System.out.println(db.containers.fromVehicle(vehicleId,"Refrigerated"));
            default:
                System.out.println("Invalid Type");
        }
    }
}