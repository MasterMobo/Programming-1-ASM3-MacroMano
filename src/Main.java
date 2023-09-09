import PortManagementSystem.CLI.CLI;
import PortManagementSystem.DB.MasterDatabase;
import PortManagementSystem.Port;
import PortManagementSystem.Vehicle.Vehicle;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        MasterDatabase db = MasterDatabase.initDB();


        CLI cli = new CLI(db);

        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine().trim();
        while (!command.equals("!q")) {
            cli.executeCommand(command);

            command = scanner.nextLine().trim();

        }
    }
}