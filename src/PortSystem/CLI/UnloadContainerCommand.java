package PortSystem.CLI;

import PortSystem.DB.MasterDatabase;
import PortSystem.User.PortManager;
import PortSystem.User.SystemAdmin;

public class UnloadContainerCommand extends Command {

    public UnloadContainerCommand() {
        super();
        signature = "unload";
        desc = "Unload containers onto a vehicle";
        usage = "unload <vehicleID>";
        arguments = 1;
    }

    @Override
    public void execute(String[] args, MasterDatabase db, CLI cli) {
        if (cli.user instanceof SystemAdmin) {
            db.containers.unloadFromVehicle(args[0], null);
        } else if (cli.user instanceof PortManager) {
            db.containers.unloadFromVehicle(args[0], ((PortManager) cli.user).getPortID());
        }
    }

}
