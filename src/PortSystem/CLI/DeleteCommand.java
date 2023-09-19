package PortSystem.CLI;

import PortSystem.Containers.Container;
import PortSystem.DB.MasterDatabase;
import PortSystem.User.PortManager;
import PortSystem.Utils.DisplayUtils;

import java.util.Objects;

public class DeleteCommand extends Command{
    public DeleteCommand() {
        signature = "del";
        desc = "Delete record of given type and id";
        usage = "del <type> <id>";
        arguments = 2;
    }

    public static void process(String[] args, MasterDatabase db, CLI cli) {
        DeleteCommand cmd = new DeleteCommand();
        if (!cmd.validateArguments(args)) {
            return;
        }

        cmd.execute(args, db, cli);
    }

    public void execute(String[] args, MasterDatabase db, CLI cli) {
        String type = args[0];
        String id = args[1];
        if (!cli.user.isAccessible(type)) {
            System.out.println("You do not have the authority to this command");
            return;
        }

        switch (type) {
            case "port":
                if (db.ports.delete(id) == null) return;
                break;
            case "vehicle":
                if (db.vehicles.delete(id) == null) return;
                break;
            case "container":
                // TODO does deleting container affect port and vehicle?
                Container container = db.containers.find(id);
                if (container == null) return;

                if (cli.user instanceof PortManager && !Objects.equals(container.portId, ((PortManager) cli.user).getPortID())) {
                    DisplayUtils.printErrorMessage("You do not have permission to delete this container");
                    return;
                }

                db.containers.delete(id);
                break;
            case "trip":
                if (db.trips.delete(id) == null) return;
                break;
            default:
                DisplayUtils.printInvalidTypeError("port, vehicle, container, trip");
        }
    }
}
