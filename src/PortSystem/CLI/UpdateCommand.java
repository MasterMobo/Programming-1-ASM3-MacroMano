package PortSystem.CLI;

import PortSystem.DB.MasterDatabase;
import PortSystem.Utils.DisplayUtils;

public class UpdateCommand extends Command{
    public UpdateCommand() {
        signature = "upd";
        desc = "Initiate update sequence of given type";
        usage = "upd <type> <id>";
        arguments = 2;
    }

    public static void process(String[] args, MasterDatabase db, CLI cli) {
        UpdateCommand cmd = new UpdateCommand();
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
                if (db.ports.updateRecord(id) == null) return;
                break;
            case "vehicle":
                if (db.vehicles.updateRecord(id) == null) return;
                break;
            case "container":
                if (db.containers.updateRecord(id) == null) return;
                break;
            default:
                DisplayUtils.printInvalidTypeError("port, vehicle, container");
        }
    }
}
