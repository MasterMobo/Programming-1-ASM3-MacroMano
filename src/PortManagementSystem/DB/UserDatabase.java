package PortManagementSystem.DB;

import PortManagementSystem.User.PortManager;
import PortManagementSystem.User.SystemAdmin;
import PortManagementSystem.User.User;

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

    public User login() {
        //Get the username and password, return if the account is authenticated
        Scanner scanner = new Scanner(System.in);
        System.out.println("Login...");

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
            System.out.println("Username already exists");
            return null;
        }

        System.out.print("Enter Password: ");
        String password = scanner.nextLine().trim();

        System.out.print("Enter role (admin, manager):");
        String role = scanner.nextLine().trim();

        if (role.equals("admin")) {
            return new SystemAdmin(username, password);
        } else if (role.equals("manager")) {
            System.out.print("Enter port ID: ");
            String portID = scanner.nextLine().trim();
            if (mdb.ports.find(portID) == null) return null;

            return new PortManager(username, password, portID);
        } else {
            System.out.println("Invalid role");
        }
//        System.out.print("Confirm Password: ");
//        String confirmPassword = scanner.nextLine();

        //Check if the username's already existed
//        for (db.users.data.get(username)) {
//            if (oldUser.getUsername() == username) {
//                System.out.println("Ivalid Username");
//                return false;
//            }
//        }
        //Check the confirmation password
//        if (password != confirmPassword) {
//            System.out.println("Invalid confirmation password");
//
//        }
        //Add user
        return new User(username, password);
    }
}
