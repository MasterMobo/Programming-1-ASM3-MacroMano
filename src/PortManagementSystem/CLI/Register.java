package PortManagementSystem.CLI;


import PortManagementSystem.Containers.Container;
import PortManagementSystem.DB.MasterDatabase;
import PortManagementSystem.Port;
import PortManagementSystem.User.User;
import PortManagementSystem.Vehicle.Vehicle;

import java.util.Scanner;

public class Register extends Command{
    public Register() {
        signature = "register";
        desc = "Initiate create object sequence of given type";
        usage = "crt <type>";
        arguments = 0;
    }

    public static void process(String[] args, MasterDatabase db) {
        Register cmd = new Register();
        if (!cmd.validateArguments(args)) {
            return;
        }
        cmd.execute(args, db);
    }

    public void execute(String[] args, MasterDatabase db) {
        Scanner scanner = new Scanner(System.in);
        User user = User.register(db);
        db.users.add(user);
        System.out.println("Successfully created new Account!");
    }
}

