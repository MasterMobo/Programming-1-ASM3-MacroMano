package PortSystem.CLI;

import PortSystem.Containers.Container;
import PortSystem.DB.MasterDatabase;
import PortSystem.User.PortManager;
import PortSystem.Utils.DisplayUtils;

import java.util.Objects;

public class DeleteCommand extends Command{
    public DeleteCommand() {
        super();
        signature = "del";
        desc = "Delete record of given type and id";
        usage = "del <type> <id>";
        arguments = 2;
    }

    @Override
    public void execute(String[] args, MasterDatabase db, CLI cli) {
        String type = args[0];
        String id = args[1];
        if (!cli.user.isAccessible(type)) {
            DisplayUtils.printErrorMessage("You do not have the authority to this command");
            return;
        }

        switch (type) {
            case "port":
                if (db.getPorts().delete(id) == null) return;
                break;
            case "vehicle":
                if (db.getVehicles().delete(id) == null) return;
                break;
            case "container":
                Container container = db.getContainers().find(id);
                if (container == null) return;

                if (cli.user instanceof PortManager && !container.getPortId().equals(((PortManager) cli.user).getPortID())) {
                    DisplayUtils.printErrorMessage("You do not have permission to delete this container");
                    return;
                }

                db.getContainers().delete(id);
                break;
            case "manager":
                if (db.getUsers().deleteManager(id) == null) return;
                break;
            case "trip":
                if (db.getTrips().delete(id) == null) return;
                break;
            default:
                DisplayUtils.printInvalidTypeError("port, vehicle, container, trip");
        }
    }
}
