package PortSystem.CLI;

import PortSystem.Containers.Container;
import PortSystem.DB.MasterDatabase;
import PortSystem.Port.Port;
import PortSystem.User.User;
import PortSystem.Vehicle.Vehicle;

public class FindCommand extends Command{
    public FindCommand() {
        signature = "find";
        desc = "Find and show the information of an object";
        usage = "find <type> <id>";
        arguments = 2;
    }

    public static void process(String[] args, MasterDatabase db, CLI cli) {
        FindCommand cmd = new FindCommand();
        if (!cmd.validateArguments(args)) {
            return;
        }
        execute(args, db, cli);
    }

    public static void execute(String[] args, MasterDatabase db, CLI cli) {
        String type = args[0];
        String id = args[1];
        switch (type) {
            case "port":
                db.ports.showInfo(id);
                break;
            case "vehicle":
                db.vehicles.showInfo(id);
                break;
            case "container":
                db.containers.showInfo(id);
                break;
            case  "trip":
                db.trips.showInfo(id);
                break;
            default:
                System.out.println("Invalid Type");
        }
    }
}


