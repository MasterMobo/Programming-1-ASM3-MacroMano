package PortSystem.CLI;


import PortSystem.Containers.Container;
import PortSystem.DB.MasterDatabase;
import PortSystem.Port.Port;
import PortSystem.User.User;
import PortSystem.Vehicle.Vehicle;

public class ShowUserInfoCommand extends Command{
    public ShowUserInfoCommand() {
        super();
        signature = "info";
        desc = "Show the information of the current user";
        usage = "info";
        arguments = 0;
        loginRequired = true;
    }

    @Override
    public void execute(String[] args, MasterDatabase db, CLI cli) {
        db.getUsers().showUserInfo(cli.user);
    }
}

