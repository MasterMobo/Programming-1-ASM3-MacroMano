package PortSystem.CLI;


import PortSystem.DB.MasterDatabase;

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

