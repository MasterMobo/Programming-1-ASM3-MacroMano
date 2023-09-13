package PortSystem.CLI;

import PortSystem.DB.MasterDatabase;

public class UnloadContainerOnVehicleCommand extends Command {

    public UnloadContainerOnVehicleCommand() {
        signature = "unloadv";
        desc = "Unload containers onto a vehicle";
        usage = "unloadv <vehicleID>";
        arguments = 1;
    }

    public static void process(String[] args, MasterDatabase db) {
        UnloadContainerOnVehicleCommand cmd = new UnloadContainerOnVehicleCommand();
        if (!cmd.validateArguments(args)) {
            return;
        }

        cmd.execute(args, db);
    }

    public void execute(String[] args, MasterDatabase db) {
        db.containers.unloadFromVehicle(args[0]);
    }

}
