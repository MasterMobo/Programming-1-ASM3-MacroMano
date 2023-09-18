package PortSystem.CLI;

import PortSystem.DB.MasterDatabase;
import PortSystem.Utils.DisplayUtils;

public class ListTripsOnDaysCommand extends Command{
    public ListTripsOnDaysCommand() {
        signature = "lstd";
        desc = "List all trips from one day to another. Expecting dd/MM/yyyy format. Type '.' for <toDay> to gets trips on <fromDay> only ";
        usage = "lstd <fromDay> <toDay>";
        arguments = 2;
    }

    public static void process(String[] args, MasterDatabase db) {
        ListTripsOnDaysCommand cmd = new ListTripsOnDaysCommand();
        if (!cmd.validateArguments(args)) {
            return;
        }

        cmd.execute(args, db);
    }

    public void execute(String[] args, MasterDatabase db) {
        String fromDay = args[0];
        String toDay = args[1];

        if (toDay.equals(".")) {
            DisplayUtils.printArray(db.trips.tripsOn(fromDay));
            return;
        }

        DisplayUtils.printArray(db.trips.tripsBetween(fromDay, toDay));
    }
}
