package PortSystem.CLI;

import PortSystem.DB.MasterDatabase;
import PortSystem.Utils.DisplayUtils;

public class LogoutCommand extends Command{
    public LogoutCommand() {
        super();
        signature = "logout";
        desc = "Log out of current user";
        usage = "logout";
        arguments = 0;
    }

    @Override
    public void execute(String[] args, MasterDatabase db, CLI cli) {
        cli.user = null;    // Detach current user
        DisplayUtils.printSystemMessage("Successfully logged out");
    }
}
