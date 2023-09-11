package PortSystem.CLI;

import PortSystem.DB.MasterDatabase;

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
                // TODO: Does deleting vehicle affect port?
                if (db.vehicles.delete(id) == null) return;
                break;
            case "container":
                // TODO: Does deleting container affect port and vehicle?
                if (db.containers.delete(id) == null) return;
                break;
            default:
                System.out.println("Invalid Type");
        }
    }
}
