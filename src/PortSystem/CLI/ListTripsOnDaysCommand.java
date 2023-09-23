package PortSystem.CLI;

import PortSystem.DB.MasterDatabase;
import PortSystem.Utils.DisplayUtils;

public class ListTripsOnDaysCommand extends Command{
    public ListTripsOnDaysCommand() {
        super();
        signature = "lstd";
        desc = "List all trips from one day to another. Expecting dd/MM/yyyy format. Type '.' for <toDay> to gets trips on <fromDay> only ";
        usage = "lstd <fromDay> <toDay>";
        arguments = 2;
    }

    @Override
    public void execute(String[] args, MasterDatabase db, CLI cli) {
        String fromDay = args[0];
        String toDay = args[1];

        if (toDay.equals(".")) {
            DisplayUtils.printArray(db.getTrips().tripsOn(fromDay));
            return;
        }

        DisplayUtils.printArray(db.getTrips().tripsBetween(fromDay, toDay));
    }
}
