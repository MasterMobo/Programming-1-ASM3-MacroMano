package PortSystem.CLI;

import PortSystem.Containers.Container;
import PortSystem.DB.MasterDatabase;
import PortSystem.Port.Port;
import PortSystem.User.User;
import PortSystem.Utils.DisplayUtils;
import PortSystem.Vehicle.Vehicle;

public class FindCommand extends Command{
    public FindCommand() {
        super();
        signature = "find";
        desc = "Find and show the information of an object";
        usage = "find <type> <id>";
        arguments = 2;
    }

    @Override
    public void execute(String[] args, MasterDatabase db, CLI cli) {
        String type = args[0];
        String id = args[1];
        switch (type) {
            case "port":
                db.ports.find(id);
                break;
            case "vehicle":
                db.vehicles.find(id);
                break;
            case "container":
                db.containers.find(id);
                break;
            case  "trip":
                db.trips.find(id);
                break;
            default:
                DisplayUtils.printInvalidTypeError("port, vehicle, container, trip");
        }
    }
}


