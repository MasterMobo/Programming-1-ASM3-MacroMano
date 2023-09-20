package PortSystem.CLI;

import PortSystem.DB.MasterDatabase;

public class StartVehicleMove extends Command{
    public StartVehicleMove() {
        signature = "svm";
        desc = "Check if vehicle can go on this trip and create new trip";
        usage = "svm <vId> <pId> <departDate>";
        arguments = 3;
    }

    public static void process(String[] args, MasterDatabase db) {
        StartVehicleMove cmd = new StartVehicleMove();
        if (!cmd.validateArguments(args)) {
            return;
        }

        cmd.execute(args, db);
    }

    public void execute(String[] args, MasterDatabase db) {
        String vId = args[0];
        String pId = args[1];
        String departDate = args[2];
        db.vehicles.startMove(vId, pId, departDate);
    }
}
