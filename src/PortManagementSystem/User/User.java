package PortManagementSystem.User;
import PortManagementSystem.DB.DatabaseRecord;
import PortManagementSystem.Database;
import PortManagementSystem.Trip;

import java.util.ArrayList;

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



    public boolean register(String username, String password, String confirmPassword) {
        //Add new user to user list

        //Check if the username's already existed
        for (User oldUser : users ) {
            if (oldUser.getUsername() == username) {
                System.out.println("Ivalid Username");
                return false;
            }
        }
        //Check the confirmation password
        if (password != confirmPassword) {
            System.out.println("Invalid confirmation password");
            return false;
        }

        //Add user
        User user = new User(username, password);
        users.add(user);
        System.out.println("successfully registered");
        return true;
    }

    public boolean login(String username, String password) {
        //Get the username and password, return if the account is authenticated

        for (User oldUser : users ) {
            if (oldUser.getUsername() == username) {
                if (oldUser.getPassword() == password) {
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
        System.out.println("Invalid Username");
        boolean authenticated = false;
        return authenticated;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}







