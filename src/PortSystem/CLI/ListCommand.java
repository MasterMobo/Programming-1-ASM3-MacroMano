package PortSystem.CLI;

import PortSystem.DB.MasterDatabase;

public class ListCommand extends Command{

    public ListCommand() {
        signature = "ls";
        desc = "List all the record of given type";
        usage = "ls <type>";
        arguments = 1;
    }

    public static void process(String[] args, MasterDatabase db) {
        ListCommand cmd = new ListCommand();
        if (!cmd.validateArguments(args)) {
            return;
        }

        cmd.execute(args, db);
    }

    public void execute(String[] args, MasterDatabase db) {
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
                System.out.println("Invalid Type");
        }
    }

}
