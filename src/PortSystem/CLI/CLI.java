package PortSystem.CLI;

import PortSystem.DB.MasterDatabase;
import PortSystem.User.User;
import PortSystem.Utils.ConsoleColors;
import PortSystem.Utils.DisplayUtils;

import java.util.HashMap;
import java.util.List;
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
        commandMap.put("svm", this::startVehicleMove);
        commandMap.put("uts", this::updateTripStatus);
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
        new ListCommand().process(args, db, this);
    }


    public void listVehiclesFromPort(String[] args) {
        if (!isLoggedIn()) return;
        new ListVehicleFromPortCommand().process(args, db, this);
    }

    public void listContainersFromVehicle(String[] args) {
        if (!isLoggedIn()) return;
        new ListContainerFromVehicleCommand().process(args, db, this);
    }

    public void listContainersFromPort(String[] args) {
        if (!isLoggedIn()) return;
        new ListContainerFromPortCommand().process(args, db, this);
    }

    public void listTripsFromPort(String[] args) {
        if (!isLoggedIn()) return;
        new ListTripsFromPortCommand().process(args, db, this);
    }

    public void listTripsOnDays(String[] args) {
        if (!isLoggedIn()) return;
        new ListTripsOnDaysCommand().process(args, db, this);
    }

    public void create(String[] args) {
        if (!isLoggedIn()) return;
        new CreateCommand().process(args, db, this);
    }

    public void update(String[] args) {
        if (!isLoggedIn()) return;
        new UpdateCommand().process(args, db, this);
    }

    public void delete(String[] args) {
        if (!isLoggedIn()) return;
        new DeleteCommand().process(args, db, this);
    }

    public void loadContainerOnVehicle(String[] args) {
        if (!isLoggedIn()) return;
        new LoadContainerCommand().process(args, db, this);
    }

    public void unloadFromVehicle(String[] args) {
        if (!isLoggedIn()) return;
        new UnloadContainerCommand().process(args, db, this);
    }

    public void startVehicleMove(String[] args) {
        if (!isLoggedIn()) return;
        new StartVehicleMove().process(args, db, this);
    }
    public void showUserInfo(String[] args) {
        if (!isLoggedIn()) return;
        new ShowUserInfoCommand().process(args, db, this);
    }

    public void findObject(String[] args) {
        if (!isLoggedIn()) return;
        new FindCommand().process(args, db, this);
    }

    public void statFuel(String[] args) {
        if (!isLoggedIn()) return;
        new StatFuelCommand().process(args, db, this);
    }

    public void statContainer(String[] args) {
        if (!isLoggedIn()) return;
        new StatContainerCommand().process(args, db, this);
    }

    public void updateTripStatus(String[] args) {
        if (!isLoggedIn()) return;;
        new UpdateTripStatus().process(args, db, this);
    }

    public void register(String[] args) {
        if (user != null) {
            System.out.println("You are already logged in, please logout first");
            return;
        }
        new RegisterCommand().process(args, db, this);
    }

    public void login(String[] args) {
        if (user != null) {
            System.out.println("You are already logged in, please logout first");
            return;
        }
        new LoginCommand().process(args, db, this);
    }

    private void logout(String[] args) {
        if (!isLoggedIn()) return;
        new LogoutCommand().process(args, db, this);
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
            + new StartVehicleMove().getInfo() + "\n"
            + new RefuelCommand().getInfo() + "\n"
            + new StatContainerCommand().getInfo() + "\n"
            + new StatFuelCommand().getInfo() + "\n"
            + new UpdateTripStatus().getInfo() + "\n"
        );
    }

}
