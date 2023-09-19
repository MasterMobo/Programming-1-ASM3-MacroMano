package PortSystem.CLI;

import PortSystem.DB.MasterDatabase;
import PortSystem.User.PortManager;
import PortSystem.User.SystemAdmin;

public class RefuelCommand extends Command{
    public RefuelCommand() {
        signature ="refuel";
        desc = "Refuel the given vehicle";
        usage ="refuel <vehicleId>";
        arguments = 1;
    }

    public static void process(String[] args, MasterDatabase db, CLI cli) {
        RefuelCommand cmd = new RefuelCommand();
        if (!cmd.validateArguments(args)) {
            return;
        }

        cmd.execute(args, db, cli);
    }

    public void execute(String[] args, MasterDatabase db, CLI cli) {
        String vId = args[0];
        if (cli.user instanceof SystemAdmin) {
            db.vehicles.refuelVehicle(vId, null);
        } else if (cli.user instanceof PortManager) {
            db.vehicles.refuelVehicle(vId, ((PortManager) cli.user).getPortID());
        }

    }
}
