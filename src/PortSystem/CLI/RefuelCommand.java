package PortSystem.CLI;

import PortSystem.DB.MasterDatabase;

public class RefuelCommand extends Command{
    public RefuelCommand() {
        signature ="refuel";
        desc = "Refuel the given vehicle";
        usage ="refuel <vehicleId>";
        arguments = 1;
    }

    public static void process(String[] args, MasterDatabase db) {
        RefuelCommand cmd = new RefuelCommand();
        if (!cmd.validateArguments(args)) {
            return;
        }

        cmd.execute(args, db);
    }

    public void execute(String[] args, MasterDatabase db) {
        String vId = args[0];
        db.vehicles.refuelVehicle(vId);
    }
}
