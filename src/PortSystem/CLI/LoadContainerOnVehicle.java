package PortSystem.CLI;

import PortSystem.DB.MasterDatabase;

public class LoadContainerOnVehicle extends Command {

    public LoadContainerOnVehicle() {
        signature = "loadv";
        desc = "Load containers onto a vehicle";
        usage = "loadv <vehicleID>";
        arguments = 1;
    }

    public static void process(String[] args, MasterDatabase db) {
        LoadContainerOnVehicle cmd = new LoadContainerOnVehicle();
        if (!cmd.validateArguments(args)) {
            return;
        }

        cmd.execute(args, db);
    }

    public void execute(String[] args, MasterDatabase db) {
        db.containers.loadContainerOnVehicle(args[0]);
    }

}
