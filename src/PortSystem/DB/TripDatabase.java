package PortSystem.DB;

import PortSystem.Containers.Container;
import PortSystem.Port.Port;
import PortSystem.Trip.Trip;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

import static PortSystem.Utils.DateUtils.toLocalDate;

public class TripDatabase extends Database<Trip>{
    // Specialized class to store Trip records
    public TripDatabase(MasterDatabase mdb) {
        super(mdb, "t");
    }
    private boolean tripExists(String tripId) {
        return mdb.trips.find(tripId) != null;
    }

    public ArrayList<Trip> tripsOn(String dateString) {
        // Expecting date in "dd/MM/yyyy" format
        // Returns all the trips departed on the given date
        ArrayList<Trip> res = new ArrayList<>();
        LocalDate date = toLocalDate(dateString);
        if (date == null) return null;

        for (Trip trip: data.values()) {
            if (trip.getDepartDate().isEqual(date)) res.add(trip);
        }

        return res;
    }

    private ArrayList<Trip> tripsOn(LocalDate date) {
        // Helper method for tripsBetween
        // Returns all the trips departed on the given date
        ArrayList<Trip> res = new ArrayList<>();

        for (Trip trip: data.values()) {
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
        if (date1 == null || date2 == null) return null;

        LocalDate currentDate = date1;

        while (!currentDate.isAfter(date2)) {
            res.addAll(tripsOn(currentDate));
            currentDate = currentDate.plusDays(1);
        }
        return res;
    }

    public ArrayList<Trip> fromPort(String portId) {
        Port port = mdb.ports.find(portId);
        if (port == null) return null;

        ArrayList<Trip> res = new ArrayList<>();

        for (Map.Entry<String, Trip> set: data.entrySet()) {
            Trip trip = set.getValue();
            if (trip.departPortId.equals(portId) || trip.arrivePortId.equals(portId)) {
                res.add(trip);
            }
        }

        return res;

    }

    public void showInfo(String tripID) {
        if (!tripExists(tripID)) {
            System.out.println("Invalid Trip ID");
            return;
        }
        Trip foundTrip = find(tripID);
        System.out.println(foundTrip.toString());
    }

    public double dayFuelConsumption(String date) {
        Double dayfuelConsumption = 0.0;

        for (Trip trip : mdb.trips.tripsOn(date)) {
            dayfuelConsumption += trip.getFuelConsumed();
        }

        return dayfuelConsumption;
    }

}
