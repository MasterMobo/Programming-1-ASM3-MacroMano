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
        loginRequired = true;
    }

    @Override
    public void execute(String[] args, MasterDatabase db, CLI cli) {
        String type = args[0];
        String id = args[1];
        switch (type) {
            case "port":
                System.out.println(db.getPorts().showInfo(id));
                break;
            case "vehicle":
                System.out.println(db.getVehicles().showInfo(id));
                break;
            case "container":
                System.out.println(db.getContainers().showInfo(id));
                break;
            case  "trip":
                System.out.println(db.getTrips().showInfo(id));
                break;
            default:
                DisplayUtils.printInvalidTypeError("port, vehicle, container, trip");
        }
    }
}


