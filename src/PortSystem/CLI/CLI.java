package PortSystem.CLI;

import PortSystem.DB.MasterDatabase;
import PortSystem.Exceptions.CommandNotFoundException;
import PortSystem.User.User;
import PortSystem.Utils.ConsoleColors;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class CLI {
    private final Map<String, Command> commandMap;
    private final MasterDatabase db;
    protected User user = null;

    public CLI(MasterDatabase db) {
        this.db = db;
        commandMap = new HashMap<>();
        commandMap.put("ls", new ListCommand());
        commandMap.put("lsv", new ListVehicleFromPortCommand());
        commandMap.put("lscv", new ListContainerFromVehicleCommand());
        commandMap.put("lscp", new ListContainerFromPortCommand());
        commandMap.put("lstp", new ListTripsFromPortCommand());
        commandMap.put("lstd", new ListTripsOnDaysCommand());
        commandMap.put("crt", new CreateCommand());
        commandMap.put("del", new DeleteCommand());
        commandMap.put("upd", new UpdateCommand());
        commandMap.put("help", new HelpCommand());
        commandMap.put("login", new LoginCommand());
        commandMap.put("logout", new LogoutCommand());
        commandMap.put("register", new RegisterCommand());
        commandMap.put("load", new LoadContainerCommand());
        commandMap.put("unload", new UnloadContainerCommand());
        commandMap.put("info", new ShowUserInfoCommand());
        commandMap.put("find", new FindCommand());
        commandMap.put("statfuel", new StatFuelCommand());
        commandMap.put("statcon", new StatContainerCommand());
        commandMap.put("vnt", new VehicleNewTrip());
        commandMap.put("uts", new UpdateTripStatus());
        commandMap.put("refuel", new RefuelCommand());
        // Add more commands as needed

    }

    public void executeCommand(String input) throws CommandNotFoundException {
        String[] parts = input.split("\\s+", 2); // Split the input into command and arguments
        String command = parts[0].trim();
        String[] arguments = parts.length > 1 ? parts[1].trim().split("\\s+") : new String[0];

        Command cmd = commandMap.get(command);
        if (cmd != null) {
            cmd.process(arguments, db, this);
        } else {
            throw new CommandNotFoundException(command);
        }
    }

    boolean isLoggedIn() {
       return user != null;
    }

}
