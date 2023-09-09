package PortManagementSystem.CLI;

import PortManagementSystem.DB.MasterDatabase;

public class ListVehicleFromPortCommand extends Command{

    public ListVehicleFromPortCommand() {
        signature = "lsv";
        desc = "List all vehicle (of given type) in a port";
        usage = "ls <type> <portId>";
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
                System.out.println(db.vehicles.fromPort(portId));
                break;
            case "truck":
                System.out.println(db.vehicles.trucksFromPort(portId));
                break;
            case "ship":
                System.out.println(db.vehicles.shipsFromPort(portId));
                break;
            default:
                System.out.println("Invalid type");
        }
    }
}
