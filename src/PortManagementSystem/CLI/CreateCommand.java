package PortManagementSystem.CLI;

import PortManagementSystem.DB.MasterDatabase;
import PortManagementSystem.Port;

public class CreateCommand extends Command implements CommandInterface{
    public CreateCommand() {
        signature = "crt";
        desc = "Initiate create object sequence of given type";
        usage = "crt <type>";
        arguments = 1;
    }

    public static void process(String[] args, MasterDatabase db) {
        CreateCommand cmd = new CreateCommand();
        if (!cmd.validateArguments(args)) {
            return;
        }

        cmd.execute(args, db);
    }

    public void execute(String[] args, MasterDatabase db) {
        switch (args[0]) {
            case "port":
                Port port = Port.createPort();
                db.ports.add(port);
                System.out.println("Successfully added new Port!");
                break;
            case "vehicle":

            default:
                System.out.println("Invalid Type");
        }
    }
}
