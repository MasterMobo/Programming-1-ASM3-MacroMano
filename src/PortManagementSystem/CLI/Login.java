package PortManagementSystem.CLI;

import PortManagementSystem.Containers.Container;
import PortManagementSystem.DB.MasterDatabase;
import PortManagementSystem.Port;
import PortManagementSystem.User.User;
import PortManagementSystem.Vehicle.Vehicle;

import java.util.Scanner;

public class Login extends Command{
    public Login() {
        signature = "register";
        desc = "Initiate create object sequence of given type";
        usage = "crt <type>";
        arguments = 0;
    }

    public static void process(String[] args, MasterDatabase db) {
        Login cmd = new Login();
        if (!cmd.validateArguments(args)) {
            return;
        }
        cmd.execute(args, db);
    }

    public void execute(String[] args, MasterDatabase db) {
        Scanner scanner = new Scanner(System.in);
        login(db);
        System.out.println("Successfully Logged in!");
    }

    public boolean login(MasterDatabase db) {
        //Get the username and password, return if the account is authenticated
        Scanner scanner = new Scanner(System.in);
        System.out.println("Login...");

        System.out.print("Enter Username: ");
        String username = scanner.nextLine();

        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        User user = db.users.find(username);
        if (user.getPassword() == password) {
            System.out.println("Login successfully");
            boolean authenticated = true;
            return authenticated;
        } else {
            System.out.println("Invalid password");
            boolean authenticated = false;
            return authenticated;
        }
    }
}


