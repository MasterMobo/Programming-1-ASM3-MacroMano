package PortSystem.CLI;

import PortSystem.DB.MasterDatabase;

public class RefuelCommand extends Command{
    public RefuelCommand() {
        signature ="";
        desc = "";
        usage ="";
        arguments = 2;
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
