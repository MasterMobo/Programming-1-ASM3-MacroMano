package PortManagementSystem.CLI;

import PortManagementSystem.DB.MasterDatabase;

public class DeleteCommand extends Command{
    public DeleteCommand() {
        signature = "del";
        desc = "Delete record of given type and id";
        usage = "del <type> <id>";
        arguments = 2;
    }

    public static void process(String[] args, MasterDatabase db) {
        DeleteCommand cmd = new DeleteCommand();
        if (!cmd.validateArguments(args)) {
            return;
        }

        cmd.execute(args, db);
    }

    public void execute(String[] args, MasterDatabase db) {
        String type = args[0];
        String id = args[1];
        switch (type) {
            case "port":
                if (db.ports.delete(id) == null) return;
                System.out.println("Successfully deleted port!");
                break;
            case "vehicle":
                if (db.vehicles.delete(id) == null) return;
                System.out.println("Successfully deleted vehicle!");
                break;
            case "container":
                if (db.containers.delete(id) == null) return;
                System.out.println("Successfully deleted container!");
                break;
            default:
                System.out.println("Invalid Type");
        }
    }
}
