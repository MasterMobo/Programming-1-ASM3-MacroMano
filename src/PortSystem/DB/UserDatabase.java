package PortSystem.DB;

import PortSystem.User.PortManager;
import PortSystem.User.SystemAdmin;
import PortSystem.User.User;
import PortSystem.Utils.DisplayUtils;

import java.util.Objects;
import java.util.Scanner;

public class UserDatabase extends Database<User>{
    // Specialized class to store User records
    public UserDatabase(MasterDatabase mdb) {
        super(mdb, "");
    }

    @Override
    public User add(User user) {
        // User do not have as randomly generated id, but the id will be username instead
        if (!isValidId(user.getUsername())) {
            System.out.println("Username already exists");
            return null;
        }
        data.put(user.getUsername(), user);
        mdb.save();
        return user;
    }

    public void showInfo(User user) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        if (!user.getPassword().equals(password)) {
            System.out.println("Invalid password");
            return;
        }
        System.out.println(user.toString());
    }

    public User deleteManager(String id) {
        User user = find(id);
        if (user == null) return null;

        if (user instanceof SystemAdmin) {
            DisplayUtils.printErrorMessage("Can not delete System Admin account");
            return null;
        }

        return delete(id);
    }

    public User login() {
        //Get the username and password, return if the account is authenticated
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Username: ");
        String username = scanner.nextLine();

        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        User foundUser = find(username);
        if (foundUser == null) return null;
        if (!Objects.equals(foundUser.getPassword(), password)) return null;
        return foundUser;
    }

    public User register() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Registering new User...");

        System.out.print("Enter Username: ");
        String username = scanner.nextLine().trim();

        if (mdb.users.exists(username)) {
            DisplayUtils.printErrorMessage("Username already exists");
            return null;
        }

        System.out.print("Enter Password: ");
        String password = scanner.nextLine().trim();

        System.out.print("Confirm Password: ");
        String confirmPassword = scanner.nextLine().trim();

        if (!password.equals(confirmPassword)) {
            DisplayUtils.printErrorMessage("Invalid confirmation password");
            return null;
        }

        System.out.print("Enter role (admin, manager):");
        String role = scanner.nextLine().trim();

        User newUser = null;

        if (role.equals("admin")) {
            newUser =  new SystemAdmin(username, password);
        } else if (role.equals("manager")) {
            System.out.print("Enter port ID: ");
            String portID = scanner.nextLine().trim();
            if (mdb.ports.find(portID) == null) return null;

            newUser = new PortManager(username, password, portID);
        }

        if (newUser == null) return null;

        add(newUser);
        return newUser;
    }
}
