package PortSystem.CLI;

import PortSystem.DB.MasterDatabase;
import PortSystem.User.User;
import PortSystem.Utils.DisplayUtils;

public class LoginCommand extends Command{

    public LoginCommand() {
        super();
        signature = "login";
        desc = "Login to user";
        usage = "login";
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
        User user =  db.getUsers().login();
        if (user == null) {
            DisplayUtils.printErrorMessage("Login Failed");
            return;
        };
        cli.user = user;    // Set current user
        DisplayUtils.printSystemMessage("Successfully Logged in!");
        System.out.println("Type 'help' for command instructions");
    }

}


