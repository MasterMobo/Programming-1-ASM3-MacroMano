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
        loginRequired = true;
    }

    @Override
    public void execute(String[] args, MasterDatabase db, CLI cli) {
        String vId = args[0];
        if (cli.user instanceof SystemAdmin) {
            db.getVehicles().refuelVehicle(vId, null);
        } else if (cli.user instanceof PortManager) {
            db.getVehicles().refuelVehicle(vId, ((PortManager) cli.user).getPortID());
        }

    }
}
