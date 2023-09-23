package PortSystem.CLI;

import PortSystem.DB.MasterDatabase;

public class VehicleNewTrip extends Command{
    public VehicleNewTrip() {
        super();
        signature = "Vnt";
        desc = "Check if vehicle can move to destination port and create new trip";
        usage = "vnt <vehicleId> <destinationPortId> <departDate>";
        arguments = 3;
        loginRequired = true;
    }

    @Override
    public void execute(String[] args, MasterDatabase db, CLI cli) {
        String vId = args[0];
        String pId = args[1];
        String departDate = args[2];
        db.getVehicles().newTrip(vId, pId, departDate);
    }
}
