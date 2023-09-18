package PortSystem.CLI;

import PortSystem.DB.MasterDatabase;
import PortSystem.User.User;
import PortSystem.Utils.DisplayUtils;

public class LoginCommand extends Command{

    public LoginCommand() {
        signature = "login";
        desc = "Initiate login sequence";
        usage = "login";
        arguments = 0;
    }

    public static void process(String[] args, MasterDatabase db, CLI cli) {
        LoginCommand cmd = new LoginCommand();
        if (!cmd.validateArguments(args)) {
            return;
        }
        execute(args, db, cli);
    }

    public static void execute(String[] args, MasterDatabase db, CLI cli) {
        User user =  db.users.login();
        if (user == null) {
            DisplayUtils.printErrorMessage("Login Failed");
            return;
        };
        cli.user = user;    // Set current user
        DisplayUtils.printSystemMessage("Successfully Logged in!");
        System.out.println("Type 'help' for command instructions");
    }

}


