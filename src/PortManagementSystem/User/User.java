package PortManagementSystem.User;
import PortManagementSystem.DB.DatabaseRecord;
import PortManagementSystem.DB.MasterDatabase;
import PortManagementSystem.Database;
import PortManagementSystem.Trip;

import java.util.ArrayList;
import java.util.Scanner;

public class User implements DatabaseRecord {
    private String username;
    private String password;
    private Database database;

    private ArrayList<User> users;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getId() {
        return username;
    }

    public void setId(String id) {
        username = id;
    }

    public boolean authenticate(String password) {
        return this.password.equals(password);
    }

    public void listTripsOn(String date) {
        // prints out the trips on a day
        ArrayList<Trip> tripList = database.tripsOn(date);
        for (Trip trip : tripList) {
            System.out.println(trip.toString());
        }
    }
    public void listTripsBetween(String date1, String date2) {
        // prints out the trips on from date 1 to date 2
        ArrayList<Trip> tripList = database.tripsBetween(date1, date2);
        for (Trip trip : tripList) {
            System.out.println(trip.toString());
        }
    }



    public static User register(MasterDatabase db) {
        //Add new user to user list
        Scanner scanner = new Scanner(System.in);
        System.out.println("Registering new User...");

        System.out.print("Enter Username: ");
        String username = scanner.nextLine();

        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        System.out.print("Confirm Password: ");
        String confirmPassword = scanner.nextLine();

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
        System.out.println("successfully registered");
        return new User(username, password);
    }






    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}







