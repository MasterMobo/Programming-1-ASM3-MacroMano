package PortSystem.CLI;

import PortSystem.Containers.Container;
import PortSystem.DB.MasterDatabase;
import PortSystem.User.PortManager;
import PortSystem.Utils.DisplayUtils;

import java.util.Objects;

public class UpdateCommand extends Command{
    public UpdateCommand() {
        super();
        signature = "upd";
        desc = "Update record of given type and id";
        usage = "upd <type> <id>";
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
                if (db.getPorts().updateRecord(id) == null) return;
                break;
            case "vehicle":
                if (db.getVehicles().updateRecord(id) == null) return;
                break;
            case "container":
                Container container = db.getContainers().find(id);
                if (container == null) return;

                if (cli.user instanceof PortManager && !Objects.equals(container.portId, ((PortManager) cli.user).getPortID())) {
                    DisplayUtils.printErrorMessage("You do not have permission to this container");
                    return;
                }

                if (db.getContainers().updateRecord(id) == null) return;
                break;
            default:
                DisplayUtils.printInvalidTypeError("port, vehicle, container");
        }
    }
}
