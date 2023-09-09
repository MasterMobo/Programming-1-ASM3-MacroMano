package PortManagementSystem.CLI;

import PortManagementSystem.DB.MasterDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class CLI {
    private final Map<String, Consumer<String[]>> commandMap;
    private final MasterDatabase db;

    public CLI(MasterDatabase db) {
        this.db = db;
        commandMap = new HashMap<>();
        commandMap.put("ls", this::list);
        commandMap.put("lsv", this::listVehiclesFromPort);
        commandMap.put("lsc", this::listContainersFromVehicle);
        commandMap.put("crt", this::create);
        commandMap.put("del", this::delete);
        commandMap.put("help", this::help);
        commandMap.put("register", this::register);

        // Add more commands as needed
    }

    // TODO: integrate user roles with commands

    public void executeCommand(String input) {
        String[] parts = input.split("\\s+", 2); // Split the input into command and arguments
        String command = parts[0].trim();
        String[] arguments = parts.length > 1 ? parts[1].trim().split("\\s+") : new String[0];

        Consumer<String[]> action = commandMap.get(command);
        if (action != null) {
            action.accept(arguments);
        } else {
            System.out.println("Command not found: " + command);
        }
    }

    public void list(String[] args) {
        ListCommand.process(args, db);
    }


    public void listVehiclesFromPort(String[] args) {
        ListVehicleFromPortCommand.process(args, db);
    }

    public void listContainersFromVehicle(String[] args) {ListContainerFromVehicleCommand.process(args, db);}

    public void create(String[] args) {
        CreateCommand.process(args, db);
    }

    public void delete(String[] args) {
        DeleteCommand.process(args, db);
    }

    public void register(String[] args) { Register.process(args, db); }

    public void login(String[] args) { Login.process(args, db); }

    public void help(String[] args) {
        ListCommand ls = new ListCommand();
        CreateCommand crt = new CreateCommand();
        ListVehicleFromPortCommand lsv = new ListVehicleFromPortCommand();
        Register register = new Register();
        Login login = new Login();

        // TODO: add more commands for help
        System.out.println("Available Commands:\n"
            + "help: List available commands\n"
            + "!q: Quit program\n"
            + ls.getUsage() + ": " + ls.getDesc() + "\n"
            + lsv.getUsage() + ": " + lsv.getDesc() + "\n"
            + crt.getUsage() + ": " + crt.getDesc()
        );
    }

}
