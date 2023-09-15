package PortSystem.CLI;

import PortSystem.DB.MasterDatabase;
import PortSystem.Utils.DisplayUtils;

public class ListTripsFromPortCommand extends Command {
    public ListTripsFromPortCommand() {
        signature = "lstp";
        desc = "List all trips in a port";
        usage = "lstp <portId>";
        arguments = 1;
    }

    public static void process(String[] args, MasterDatabase db) {
        ListTripsFromPortCommand cmd = new ListTripsFromPortCommand();
        if (!cmd.validateArguments(args)) {
            return;
        }

        cmd.execute(args, db);
    }

    public void execute(String[] args, MasterDatabase db) {
        DisplayUtils.print(db.trips.fromPort(args[0]));
    }
}