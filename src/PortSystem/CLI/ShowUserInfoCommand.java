package PortSystem.CLI;


import PortSystem.Containers.Container;
import PortSystem.DB.MasterDatabase;
import PortSystem.Port.Port;
import PortSystem.User.User;
import PortSystem.Vehicle.Vehicle;

public class ShowUserInfoCommand extends Command{
    public ShowUserInfoCommand() {
        signature = "info";
        desc = "Show the information of the current user";
        usage = "info";
        arguments = 0;
    }

    public static void process(String[] args, MasterDatabase db, CLI cli) {
        ShowUserInfoCommand cmd = new ShowUserInfoCommand();
        if (!cmd.validateArguments(args)) {
            return;
        }
        execute(args, db, cli);
    }

    public static void execute(String[] args, MasterDatabase db, CLI cli) {
        db.users.showInfo(cli.user);
    }
}

