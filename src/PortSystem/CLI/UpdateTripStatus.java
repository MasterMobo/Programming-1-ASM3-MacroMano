package PortSystem.CLI;

import PortSystem.DB.MasterDatabase;
import PortSystem.User.PortManager;
import PortSystem.User.SystemAdmin;

public class UpdateTripStatus extends Command{
    public UpdateTripStatus() {
        super();
        signature = "uts";
        desc = "Update Trip Status";
        usage = "uts <tripId>";
        arguments = 1;
    }

    @Override
    public void execute(String[] args, MasterDatabase db, CLI cli) {
        String tripId = args[0];
        db.trips.updateTripStatus(tripId);
    }
}
