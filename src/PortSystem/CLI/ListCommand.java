package PortSystem.CLI;

import PortSystem.DB.MasterDatabase;
import PortSystem.Utils.ConsoleColors;
import PortSystem.Utils.DisplayUtils;

public class ListCommand extends Command{

    public ListCommand() {
        super();
        signature = "ls";
        desc = "List all the record of given type";
        usage = "ls <type>";
        arguments = 1;
    }

    @Override
    public void execute(String[] args, MasterDatabase db, CLI cli) {
        String type = args[0];
        switch (type) {
            case "port":
                db.getPorts().display();
                break;
            case "vehicle":
                db.getVehicles().display();
                break;
            case "container":
                db.getContainers().display();
                break;
            case "trip":
                db.getTrips().display();
                break;
            case "manager":
                DisplayUtils.printArray(db.getUsers().getManagers());
                break;
            default:
                DisplayUtils.printInvalidTypeError("port, vehicle, container, trip, manager");
        }
    }

}
