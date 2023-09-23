package PortSystem.CLI;

import PortSystem.Containers.Container;
import PortSystem.DB.MasterDatabase;
import PortSystem.Port.Port;
import PortSystem.User.PortManager;
import PortSystem.User.SystemAdmin;
import PortSystem.Utils.DisplayUtils;
import PortSystem.Vehicle.Vehicle;

public class CreateCommand extends Command{
    public CreateCommand() {
        super();
        signature = "crt";
        desc = "Create record of given type";
        usage = "crt <type>";
        arguments = 1;
        loginRequired = true;
    }

    @Override
    public void execute(String[] args, MasterDatabase db, CLI cli) {
        if (!cli.user.isAccessible(args[0])) {
            DisplayUtils.printErrorMessage("You do not have the authority to this command");
            return;
        }

        switch (args[0]) {
            case "port":
                Port port = Port.createPort();
                if (db.getPorts().createRecord(port) == null) return;
                break;
            case "vehicle":
                Vehicle vehicle = Vehicle.createVehicle();
                if (db.getVehicles().createRecord(vehicle) == null) return;
                break;
            case "container":
                String portId = null;
                if (cli.user instanceof PortManager) {
                    portId = ((PortManager) cli.user).getPortID();
                }

                Container container = Container.createContainer();
                if (db.getContainers().createRecord(container, portId) == null) return;
                break;
            default:
                DisplayUtils.printInvalidTypeError("port, vehicle, container");
        }
    }
}
