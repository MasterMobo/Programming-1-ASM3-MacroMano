package PortSystem.CLI;

import PortSystem.DB.MasterDatabase;

public class LoadContainerCommand extends Command {

    public LoadContainerCommand() {
        signature = "load";
        desc = "Load containers onto a vehicle";
        usage = "load <vehicleID>";
        arguments = 1;
    }

    public static void process(String[] args, MasterDatabase db) {
        LoadContainerCommand cmd = new LoadContainerCommand();
        if (!cmd.validateArguments(args)) {
            return;
        }

        cmd.execute(args, db);
    }

    public void execute(String[] args, MasterDatabase db) {
        db.containers.loadContainerOnVehicle(args[0]);
    }

}
