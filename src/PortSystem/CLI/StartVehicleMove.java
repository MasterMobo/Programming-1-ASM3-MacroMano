package PortSystem.CLI;

import PortSystem.DB.MasterDatabase;

public class StartVehicleMove extends Command{
    public StartVehicleMove() {
        super();
        signature = "svm";
        desc = "Check if vehicle can move to destination port and create new trip";
        usage = "svm <vehicleId> <destinationPortId> <departDate>";
        arguments = 3;
    }

    @Override
    public void execute(String[] args, MasterDatabase db, CLI cli) {
        String vId = args[0];
        String pId = args[1];
        String departDate = args[2];
        db.getVehicles().startMove(vId, pId, departDate);
    }
}
