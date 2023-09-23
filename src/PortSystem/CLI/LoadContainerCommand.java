package PortSystem.CLI;

import PortSystem.DB.MasterDatabase;
import PortSystem.User.PortManager;
import PortSystem.User.SystemAdmin;

public class LoadContainerCommand extends Command {

    public LoadContainerCommand() {
        super();
        signature = "load";
        desc = "Load containers onto a vehicle";
        usage = "load <vehicleID>";
        arguments = 1;
    }

    @Override
    public void execute(String[] args, MasterDatabase db, CLI cli) {
        if (cli.user instanceof SystemAdmin) {
            db.getContainers().loadContainerOnVehicle(args[0], null);
        } else if (cli.user instanceof PortManager) {
            db.getContainers().loadContainerOnVehicle(args[0], ((PortManager) cli.user).getPortID());
        }
    }

}
