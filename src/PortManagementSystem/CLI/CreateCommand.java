package PortManagementSystem.CLI;

import PortManagementSystem.DB.MasterDatabase;
import PortManagementSystem.Port;
import PortManagementSystem.Vehicle.Vehicle;

import java.util.Scanner;

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
                Vehicle vehicle = Vehicle.createVehicle();
                if (vehicle == null) return;

                Scanner scanner = new Scanner(System.in);
                System.out.println("Enter port ID: ");
                Port p = db.ports.find(scanner.nextLine().trim());
                if (p == null) return;
                System.out.println("Successfully created vehicle!");

                //TODO restructure port in Vehicle
                vehicle.portId = p.getId();
                vehicle.port = p;
                db.vehicles.add(vehicle);
                break;
            default:
                System.out.println("Invalid Type");
        }
    }
}
