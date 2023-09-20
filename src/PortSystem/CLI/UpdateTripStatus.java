package PortSystem.CLI;

import PortSystem.DB.MasterDatabase;
import PortSystem.User.PortManager;
import PortSystem.User.SystemAdmin;

public class UpdateTripStatus extends Command{
    public UpdateTripStatus() {
        signature = "uts";
        desc = "Update Trip Status";
        usage = "uts <tripId>";
        arguments = 1;
    }

    public static void process(String[] args, MasterDatabase db) {
        UpdateTripStatus cmd = new UpdateTripStatus();
        if (!cmd.validateArguments(args)) {
            return;
        }

        cmd.execute(args, db);
    }

    public void execute(String[] args, MasterDatabase db) {
        String tripId = args[0];
        db.trips.updateTripStatus(tripId);
    }
}
