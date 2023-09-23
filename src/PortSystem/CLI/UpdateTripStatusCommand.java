package PortSystem.CLI;

import PortSystem.DB.MasterDatabase;

public class UpdateTripStatusCommand extends Command{
    public UpdateTripStatusCommand() {
        super();
        signature = "uts";
        desc = "Update Trip Status";
        usage = "uts <tripId>";
        arguments = 1;
        loginRequired = true;
    }

    @Override
    public void execute(String[] args, MasterDatabase db, CLI cli) {
        String tripId = args[0];
        db.getTrips().updateTripStatus(tripId);
    }
}
