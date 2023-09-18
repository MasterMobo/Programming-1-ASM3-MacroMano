package PortSystem.CLI;

import PortSystem.DB.MasterDatabase;

public class UnloadContainerCommand extends Command {

    public UnloadContainerCommand() {
        signature = "unload";
        desc = "Unload containers onto a vehicle";
        usage = "unload <vehicleID>";
        arguments = 1;
    }

    public static void process(String[] args, MasterDatabase db) {
        UnloadContainerCommand cmd = new UnloadContainerCommand();
        if (!cmd.validateArguments(args)) {
            return;
        }

        cmd.execute(args, db);
    }

    public void execute(String[] args, MasterDatabase db) {
        db.containers.unloadFromVehicle(args[0]);
    }

}
