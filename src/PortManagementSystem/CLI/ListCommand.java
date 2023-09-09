package PortManagementSystem.CLI;

import PortManagementSystem.DB.MasterDatabase;

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
        switch (args[0]) {
            case "port":
                db.ports.display();
                break;
            case "vehicle":
                db.vehicles.display();
                break;
            default:
                System.out.println("Invalid Type");
        }
    }

}
