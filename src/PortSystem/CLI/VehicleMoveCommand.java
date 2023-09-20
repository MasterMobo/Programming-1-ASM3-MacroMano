package PortSystem.CLI;

import PortSystem.DB.MasterDatabase;

public class VehicleMoveCommand extends Command{
    public VehicleMoveCommand() {
        super();
        signature = "vmc";
        desc = "Command to make vehicle embark the assigned trip";
        usage = "vmc <vId>";
        arguments = 1;
    }

    @Override
    public void execute(String[] args, MasterDatabase db, CLI cli) {
        String vId = args[0];
        db.vehicles.move(vId);
    }
}
