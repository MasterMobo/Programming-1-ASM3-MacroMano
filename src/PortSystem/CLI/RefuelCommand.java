package PortSystem.CLI;

import PortSystem.DB.MasterDatabase;
import PortSystem.User.PortManager;
import PortSystem.User.SystemAdmin;

public class RefuelCommand extends Command{
    public RefuelCommand() {
        super();
        signature ="refuel";
        desc = "Refuel the given vehicle";
        usage ="refuel <vehicleId>";
        arguments = 1;
    }

    @Override
    public void execute(String[] args, MasterDatabase db, CLI cli) {
        String vId = args[0];
        if (cli.user instanceof SystemAdmin) {
            db.vehicles.refuelVehicle(vId, null);
        } else if (cli.user instanceof PortManager) {
            db.vehicles.refuelVehicle(vId, ((PortManager) cli.user).getPortID());
        }

    }
}
