package PortManagementSystem.CLI;

import PortManagementSystem.Containers.Container;
import PortManagementSystem.DB.MasterDatabase;
import PortManagementSystem.Port;
import PortManagementSystem.Vehicle.Vehicle;

import java.util.Scanner;

public class CreateCommand extends Command{
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
        Scanner scanner = new Scanner(System.in);

        switch (args[0]) {
            case "port":
                Port port = Port.createPort();
                db.ports.add(port);
                System.out.println("Successfully created new Port!");
                break;
            case "vehicle":
                Vehicle vehicle = Vehicle.createVehicle();
                if (vehicle == null) return;

                System.out.print("Enter port ID: ");
                Port p = db.ports.find(scanner.nextLine().trim());
                if (p == null) return;

                // TODO restructure port in Vehicle (only store portId, not port obj)
                vehicle.portId = p.getId();
                vehicle.port = p;
                db.vehicles.add(vehicle);
                System.out.println("Successfully created vehicle!");
                break;
            case "container":
                Container container = Container.createContainer();
                if (container == null) return;

                System.out.print("Enter vehicle ID: ");
                Vehicle v = db.vehicles.find(scanner.nextLine().trim());
                if (v == null) return;

                // TODO: check if container can be placed on vehicle
                container.vehicleId = v.getId();
                db.containers.add(container);
                System.out.println("Successfully created container!");
                break;
            default:
                System.out.println("Invalid Type");
        }
    }
}
