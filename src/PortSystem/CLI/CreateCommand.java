package PortSystem.CLI;

import PortSystem.Containers.Container;
import PortSystem.DB.MasterDatabase;
import PortSystem.Port.Port;
import PortSystem.Vehicle.Vehicle;

public class CreateCommand extends Command{
    public CreateCommand() {
        signature = "crt";
        desc = "Initiate create object sequence of given type";
        usage = "crt <type>";
        arguments = 1;
    }

    public static void process(String[] args, MasterDatabase db, CLI cli) {
        CreateCommand cmd = new CreateCommand();
        if (!cmd.validateArguments(args)) {
            return;
        }

        cmd.execute(args, db, cli);
    }

    public void execute(String[] args, MasterDatabase db, CLI cli) {
        if (!cli.user.isAccessible(args[0])) {
            System.out.println("You do not have the authority to this command");
            return;
        }

        switch (args[0]) {
            case "port":
                Port port = Port.createPort();
                if (db.ports.createRecord(port) == null) return;
                break;
            case "vehicle":
                Vehicle vehicle = Vehicle.createVehicle();
                if (db.vehicles.createRecord(vehicle) == null) return;
                break;
            case "container":
                Container container = Container.createContainer();
                if (db.containers.createRecord(container) == null) return;
                break;
            default:
                System.out.println("Invalid Type");
        }
    }
}
