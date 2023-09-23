package PortSystem.CLI;

import PortSystem.DB.MasterDatabase;
import PortSystem.Utils.DisplayUtils;

public class ListTripsFromPortCommand extends Command {
    public ListTripsFromPortCommand() {
        super();
        signature = "lstp";
        desc = "List all trips in a port";
        usage = "lstp <portId>";
        arguments = 1;
        loginRequired = true;
    }

    @Override
    public void execute(String[] args, MasterDatabase db, CLI cli) {
        DisplayUtils.printArray(db.getTrips().fromPort(args[0]));
    }
}
