package PortManagementSystem;

import PortManagementSystem.Containers.Container;
import PortManagementSystem.Port;
import PortManagementSystem.User.*;
import PortManagementSystem.Vehicle.Vehicle;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import static PortManagementSystem.Utils.DBUtils.*;
import static PortManagementSystem.Utils.DateUtils.*;

public class Database {
    private ArrayList<User> users;
    private ArrayList<Port> ports;
    private ArrayList<Trip> trips;
    private ArrayList<Vehicle> vehicles;

    // TODO: maybe use HashMap instead of ArrayList to store data?
    // maps container id to container object (This is just a test)
    private HashMap<String, Container> containers = new HashMap<>();
    private static final int KEY_LENGTH = 5;

    public Object findById (String type, String id) {
        switch (type) {
            case "container":
                return containers.get(id);
            default:
                System.out.println("Invalid type");
                return null;
        }
    }

    // TODO: how the fuck do i implement unique check?
    public String generateId (String type) {
        switch (type) {
            case "container":
                return "c-" + randKey(KEY_LENGTH);
            default:
                System.out.println("Invalid type");
                return null;
        }
    }

    public void refresh() {
        // Deletes all trip records that are older than 7 days
        LocalDate now = LocalDate.now();
        for (Trip trip: trips){
            if (daysBetween(trip.getDepartDate(), now) > 7) {
                trips.remove(trip);
            }
        }
    }

    public void addAdmin(String username, String password) {
        users.add(new SystemAdmin(username, password));
    }

    public void addManager(String username, String password, String portName) {
        users.add(new PortManager(username, password, portName));
    }

    public User findByUsername(String username) {
        for (User user: users) {
            if (Objects.equals(user.getUsername(), username)) return user;
        }
        return null;
    }

    public void addPort(String name, String id, double lat, double lon, double capacity, boolean isLanding) {
        ports.add(new Port(name, id, lat, lon, capacity, isLanding));
    }

    public ArrayList<Trip> tripsOn(String dateString) {
        // Expecting date in "dd/MM/yyyy" format
        // Returns all the trips departed on the given date
        ArrayList<Trip> res = new ArrayList<>();
        LocalDate date = toLocalDate(dateString);

        for (Trip trip: trips) {
            if (trip.getDepartDate().isEqual(date)) res.add(trip);
        }

        return res;
    }

    private ArrayList<Trip> tripsOn(LocalDate date) {
        // Helper method for tripsBetween
        // Returns all the trips departed on the given date
        ArrayList<Trip> res = new ArrayList<>();

        for (Trip trip: trips) {
            if (trip.getDepartDate().isEqual(date)) res.add(trip);
        }

        return res;
    }

    public ArrayList<Trip> tripsBetween(String dateString1, String dateString2) {
        // Expecting date in "dd/MM/yyyy" format
        // Returns all the trips departed between one day and another
        ArrayList<Trip> res = new ArrayList<>();
        LocalDate date1 = toLocalDate(dateString1);
        LocalDate date2 = toLocalDate(dateString2);
        LocalDate currentDate = date1;

        while (!currentDate.isAfter(date2)) {
            res.addAll(tripsOn(currentDate));
            currentDate = currentDate.plusDays(1);
        }
        return res;
    }
}
