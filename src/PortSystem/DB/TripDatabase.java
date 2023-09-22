package PortSystem.DB;

import PortSystem.Containers.Container;
import PortSystem.Port.Port;
import PortSystem.Trip.Trip;
import PortSystem.Trip.TripStatus;
import PortSystem.Utils.DateUtils;
import PortSystem.Utils.DisplayUtils;
import PortSystem.Vehicle.Vehicle;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

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
            if (trip.getDepartPortId().equals(portId) || trip.getArrivePortId().equals(portId)) {
                res.add(trip);
            }
        }

        return res;

    }

    public double fuelConsumed(String dayString) {
        double res = 0;

        ArrayList<Trip> trips = tripsOn(dayString);
        if (trips == null) return -1;

        for (Trip trip: trips) {
            res += trip.getFuelConsumed();
        }

        return res;
    }

    public void updateTripStatus(String tripId) {
        Trip trip = mdb.trips.find(tripId);
        if (trip == null) return;

        Vehicle v = mdb.vehicles.find(trip.getVehicleId());

        if (trip.getStatus().equals(TripStatus.FULFILLED)) {
            DisplayUtils.printErrorMessage("This trip is already fulfilled, can not update further");
            return;
        }

//        Show current status
        DisplayUtils.printSystemMessage("The current trip status is: " + trip.getStatus());
        DisplayUtils.printSystemMessage("Do you want to update trip status? (y/n)");
        Scanner scanner = new Scanner(System.in);
        String prompt = scanner.nextLine();

        if (prompt.equals("y")) {
            if (trip.getStatus().equals(TripStatus.PROCESSING)) {
                DisplayUtils.printSystemMessage("Trip is initiated! Vehicle is on the way");
                trip.setStatus(TripStatus.EN_ROUTE);
                v.setPortId(null);
                v.setCurfuelCapacity(v.getFuelCapacity() - trip.getFuelConsumed());
                mdb.save();
                return;
            }

            if (trip.getStatus().equals(TripStatus.EN_ROUTE)) {
                DisplayUtils.printSystemMessage("Trip is fulfilled! Vehicle has arrived at destination");
                trip.setStatus(TripStatus.FULFILLED);
                v.setPortId(trip.getArrivePortId());
                trip.setArriveDate(LocalDate.now());
                mdb.save();
                return;
            }
        }

        if (prompt.equals("n")) {
            DisplayUtils.printErrorMessage("Update canceled");
            return;
        }

        // TODO maybe throw exception if input is wrong?
//        throw new Exception("Invalid input, Expecting 'y' or 'n");
    }

// TODO do you even need this? just use find()

//    public void showInfo(String tripID) {
//        if (!tripExists(tripID)) {
//            System.out.println("Invalid Trip ID");
//            return;
//        }
//        Trip foundTrip = find(tripID);
//        System.out.println(foundTrip.toString());
//    }
    

}
