package PortSystem.CLI;


import PortSystem.DB.MasterDatabase;
import PortSystem.User.User;
import PortSystem.Utils.DisplayUtils;

public class RegisterCommand extends Command{
    public RegisterCommand() {
        super();
        signature = "register";
        desc = "Register new user";
        usage = "register";
        arguments = 0;
        loginRequired = false;
    }

    @Override
    public void process(String[] args, MasterDatabase db, CLI cli) {
        if (cli.isLoggedIn()) {
            DisplayUtils.printErrorMessage("You are currently logged in. Please logout first");
            return;
        }

        if (!validArguments(args)) {
            DisplayUtils.printErrorMessage("Expected " + arguments + " argument(s), but received " + args.length);
            DisplayUtils.printErrorMessage("Usage: " + usage);
            return;
        }

        execute(args, db, cli);
    }

    @Override
    public void execute(String[] args, MasterDatabase db, CLI cli) {
        User user = db.getUsers().register();
        if (user == null) return;
        cli.user = user;       // Set current user
        DisplayUtils.printSystemMessage("Successfully created new Account!");
        DisplayUtils.printSystemMessage("Logged into new account");
        System.out.println("Type 'help' for command instructions");
    }
}

