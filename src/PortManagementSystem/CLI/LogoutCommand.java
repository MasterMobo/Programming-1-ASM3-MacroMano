package PortManagementSystem.CLI;

import PortManagementSystem.DB.MasterDatabase;
import PortManagementSystem.User.User;

public class LogoutCommand extends Command{
    public LogoutCommand() {
        signature = "logout";
        desc = "Log out of current user";
        usage = "logout";
        arguments = 0;
    }

    public static void process(String[] args, MasterDatabase db, CLI cli) {
        LogoutCommand cmd = new LogoutCommand();
        if (!cmd.validateArguments(args)) {
            return;
        }
        execute(args, db, cli);
    }

    public static void execute(String[] args, MasterDatabase db, CLI cli) {
        cli.user = null;    // Detach current user
        System.out.println("Successfully logged out");
    }
}
