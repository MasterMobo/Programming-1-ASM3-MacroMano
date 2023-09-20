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
                db.ports.display();
                break;
            case "vehicle":
                db.vehicles.display();
                break;
            case "container":
                db.containers.display();
                break;
            case "trip":
                db.trips.display();
                break;
            default:
                DisplayUtils.printInvalidTypeError("port, vehicle, container, trip");
        }
    }

}
