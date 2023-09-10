package PortManagementSystem.CLI;


import PortManagementSystem.DB.MasterDatabase;
import PortManagementSystem.User.User;

import java.util.Scanner;

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
        db.users.add(user);
        cli.user = user;       // Set current user
        System.out.println("Successfully created new Account!");
        System.out.println("Logged into new account");
    }
}

