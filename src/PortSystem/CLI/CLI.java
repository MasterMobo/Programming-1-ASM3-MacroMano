package PortSystem.CLI;

import PortSystem.DB.MasterDatabase;
import PortSystem.User.User;
import PortSystem.Utils.ConsoleColors;
import PortSystem.Utils.DisplayUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class CLI {
    private final Map<String, Consumer<String[]>> commandMap;
    private final MasterDatabase db;
    protected User user = null;

    public CLI(MasterDatabase db) {
        this.db = db;
        commandMap = new HashMap<>();
        commandMap.put("ls", this::list);
        commandMap.put("lsv", this::listVehiclesFromPort);
        commandMap.put("lscv", this::listContainersFromVehicle);
        commandMap.put("lscp", this::listContainersFromPort);
        commandMap.put("lstp", this::listTripsFromPort);
        commandMap.put("lstd", this::listTripsOnDays);
        commandMap.put("crt", this::create);
        commandMap.put("del", this::delete);
        commandMap.put("upd", this::update);
        commandMap.put("help", this::help);
        commandMap.put("login", this::login);
        commandMap.put("logout", this::logout);
        commandMap.put("register", this::register);
        commandMap.put("load", this::loadContainerOnVehicle);
        commandMap.put("unload", this::unloadFromVehicle);
        commandMap.put("info", this::showUserInfo);
        commandMap.put("find", this::findObject);
        commandMap.put("statfuel", this::statFuel);
        commandMap.put("statcon", this::statContainer);
        commandMap.put("vmc", this::moveVehicle);
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
            DisplayUtils.printErrorMessage("Command not found: " + command);
        }
    }

    private boolean isLoggedIn() {
        if (user == null) {
            System.out.println("Please login before continue");
            return false;
        }
        return true;
    }


    public void list(String[] args) {
        if (!isLoggedIn()) return;
        ListCommand.process(args, db);
    }


    public void listVehiclesFromPort(String[] args) {
        if (!isLoggedIn()) return;
        ListVehicleFromPortCommand.process(args, db);
    }

    public void listContainersFromVehicle(String[] args) {
        if (!isLoggedIn()) return;
        ListContainerFromVehicleCommand.process(args, db);
    }

    public void listContainersFromPort(String[] args) {
        if (!isLoggedIn()) return;
        ListContainerFromPortCommand.process(args, db);
    }

    public void listTripsFromPort(String[] args) {
        if (!isLoggedIn()) return;
        ListTripsFromPortCommand.process(args, db);
    }

    public void listTripsOnDays(String[] args) {
        if (!isLoggedIn()) return;
        ListTripsOnDaysCommand.process(args, db);
    }

    public void create(String[] args) {
        if (!isLoggedIn()) return;
        CreateCommand.process(args, db, this);
    }

    public void update(String[] args) {
        if (!isLoggedIn()) return;
        UpdateCommand.process(args, db, this);
    }

    public void delete(String[] args) {
        if (!isLoggedIn()) return;
        DeleteCommand.process(args, db, this);
    }

    public void loadContainerOnVehicle(String[] args) {
        if (!isLoggedIn()) return;
        LoadContainerCommand.process(args, db, this);
    }

    public void unloadFromVehicle(String[] args) {
        if (!isLoggedIn()) return;
        UnloadContainerCommand.process(args, db, this);
    }

    public void moveVehicle(String[] args) {
        if (!isLoggedIn()) return;
        VehicleMoveCommand.process(args, db);
    }
    public void showUserInfo(String[] args) {
        if (!isLoggedIn()) return;
        ShowUserInfoCommand.process(args, db, this);
    }

    public void findObject(String[] args) {
        if (!isLoggedIn()) return;
        FindCommand.process(args, db, this);
    }

    public void statFuel(String[] args) {
        if (!isLoggedIn()) return;
        StatFuelCommand.process(args, db);
    }

    public void statContainer(String[] args) {
        if (!isLoggedIn()) return;
        StatContainerCommand.process(args, db);
    }


    public void register(String[] args) {
        if (user != null) {
            System.out.println("You are already logged in, please logout first");
            return;
        }
        RegisterCommand.process(args, db, this);
    }

    public void login(String[] args) {
        if (user != null) {
            System.out.println("You are already logged in, please logout first");
            return;
        }
        LoginCommand.process(args, db, this);
    }

    private void logout(String[] args) {
        if (!isLoggedIn()) return;
        LogoutCommand.process(args, db, this);
    }

    public void help(String[] args) {

        // TODO: add more commands for help
        System.out.println(ConsoleColors.CYAN_UNDERLINED + "Available Commands:\n" + ConsoleColors.RESET
            + new HelpCommand().getInfo() + "\n"
            + new QuitCommand().getInfo() + "\n"
            + new LoginCommand().getInfo() + "\n"
            + new RegisterCommand().getInfo() + "\n"
            + new LogoutCommand().getInfo() + "\n"
            + new ShowUserInfoCommand().getInfo() + "\n"
            + new ListCommand().getInfo() + "\n"
            + new ListContainerFromVehicleCommand().getInfo() + "\n"
            + new ListContainerFromPortCommand().getInfo() + "\n"
            + new ListVehicleFromPortCommand().getInfo() + "\n"
            + new ListTripsFromPortCommand().getInfo() + "\n"
            + new ListTripsOnDaysCommand().getInfo() + "\n"
            + new FindCommand().getInfo() + "\n"
            + new CreateCommand().getInfo() + "\n"
            + new DeleteCommand().getInfo() + "\n"
            + new UpdateCommand().getInfo() + "\n"
            + new LoadContainerCommand().getInfo() + "\n"
            + new UnloadContainerCommand().getInfo() + "\n"
            + new VehicleMoveCommand().getInfo() + "\n"
            + new RefuelCommand().getInfo() + "\n"
            + new StatContainerCommand().getInfo() + "\n"
            + new StatFuelCommand().getInfo() + "\n"
        );
    }

}
