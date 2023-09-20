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
    }

    @Override
    public void execute(String[] args, MasterDatabase db, CLI cli) {
        DisplayUtils.printArray(db.trips.fromPort(args[0]));
    }
}
