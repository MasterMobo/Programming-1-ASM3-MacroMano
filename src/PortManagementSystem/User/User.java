package PortManagementSystem.User;
import PortManagementSystem.Database;
import PortManagementSystem.Trip;

import java.util.ArrayList;

public class User {
    private String username;
    private String password;
    private Database database;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
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


}







