package PortSystem.CLI;


import PortSystem.DB.MasterDatabase;
import PortSystem.User.User;
import PortSystem.Utils.DisplayUtils;

public class RegisterCommand extends Command{
    public RegisterCommand() {
        signature = "register";
        desc = "Initiate register object sequence";
        usage = "register";
        arguments = 0;
    }

    public static void process(String[] args, MasterDatabase db, CLI cli) {
        RegisterCommand cmd = new RegisterCommand();
        if (!cmd.validateArguments(args)) {
            return;
        }
        cmd.execute(args, db, cli);
    }

    public void execute(String[] args, MasterDatabase db, CLI cli) {
        User user = db.users.register();
        if (user == null) return;
        cli.user = user;       // Set current user
        DisplayUtils.printSystemMessage("Successfully created new Account!");
        DisplayUtils.printSystemMessage("Logged into new account");
        System.out.println("Type 'help' for command instructions");
    }
}

