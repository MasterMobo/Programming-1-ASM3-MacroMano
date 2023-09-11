package PortSystem.CLI;

import PortSystem.DB.MasterDatabase;
import PortSystem.User.User;

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
        commandMap.put("lsc", this::listContainersFromVehicle);
        commandMap.put("crt", this::create);
        commandMap.put("del", this::delete);
        commandMap.put("upd", this::update);
        commandMap.put("help", this::help);
        commandMap.put("login", this::login);
        commandMap.put("logout", this::logout);
        commandMap.put("register", this::register);
        commandMap.put("loadv", this::loadContainerOnVehicle);
        commandMap.put("info", this::showUserInfo);
        commandMap.put("find", this::findObject);

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
        LoadContainerOnVehicle.process(args, db);
    }

    public void showUserInfo(String[] args) {
        if (!isLoggedIn()) return;
        ShowUserInfoCommand.process(args, db, this);
    }

    public void findObject(String[] args) {
        if (!isLoggedIn()) return;
        FindCommand.process(args, db, this);
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
        ListCommand ls = new ListCommand();
        CreateCommand crt = new CreateCommand();
        ListVehicleFromPortCommand lsv = new ListVehicleFromPortCommand();
        RegisterCommand register = new RegisterCommand();
        LoginCommand login = new LoginCommand();

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
