package PortSystem.CLI;

import PortSystem.DB.MasterDatabase;

public class VehicleMoveCommand extends Command{
    public VehicleMoveCommand() {
        signature = "vmc";
        desc = "Command to make vehicle embark the assigned trip";
        usage = "vmc <vId>";
        arguments = 1;
    }

    public static void process(String[] args, MasterDatabase db) {
        VehicleMoveCommand cmd = new VehicleMoveCommand();
        if (!cmd.validateArguments(args)) {
            return;
        }

        cmd.execute(args, db);
    }

    public void execute(String[] args, MasterDatabase db) {
        String vId = args[0];
        db.vehicles.move(vId);
    }
}
