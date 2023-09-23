package PortSystem.DB;

import PortSystem.Port.Port;
import PortSystem.Trip.Trip;
import PortSystem.Trip.TripStatus;
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
        return mdb.getTrips().find(tripId) != null;
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
        Port port = mdb.getPorts().find(portId);
        if (port == null) return null;

        ArrayList<Trip> res = new ArrayList<>();

        for (Map.Entry<String, Trip> set: data.entrySet()) {
            Trip trip = set.getValue();
            if (Objects.equals(trip.getDepartPortId(), portId) || Objects.equals(trip.getArrivePortId(), portId)) {
                res.add(trip);
            }
        }

        return res;

    }

    public ArrayList<Trip> fromVehicle(String vehicleId) {
        Vehicle vehicle = mdb.getVehicles().find(vehicleId);
        if (vehicle == null) return null;
        ArrayList<Trip> res = new ArrayList<>();

        for (Map.Entry<String, Trip> set: data.entrySet()) {
            Trip trip = set.getValue();
            if (Objects.equals(trip.getVehicleId(), vehicleId)) {
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
        Trip trip = mdb.getTrips().find(tripId);
        if (trip == null) return;


        Vehicle v = mdb.getVehicles().find(trip.getVehicleId());


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
                v.setCurFuelAmount(v.getFuelCapacity() - trip.getFuelConsumed());
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

        DisplayUtils.printErrorMessage("Invalid input, Expecting 'y' or 'n\'");
    }

    @Override
    public String showInfo(String tripID) {
        Trip foundTrip = find(tripID);
        return "Trip{" +
                "\n     ID: " + foundTrip.getId() + ", " +
                "\n     Vehicle ID: " + foundTrip.getVehicleId()  + ", " +
                "\n     Depart Port ID: " + foundTrip.getDepartPortId() + ", Arrive Port ID: " + foundTrip.getArrivePortId() + ", " +
                "\n     Depart Date: " + foundTrip.getDepartDate() + ", Arrive Date: " + foundTrip.getArriveDate() + ", " +
                "\n     Distance: " + foundTrip.getDistance() + ", Fuel Consumed: " + foundTrip.getFuelConsumed() + ", " +
                "\n     Status: " + foundTrip.getStatus() +
                "\n     }";
    }

}
