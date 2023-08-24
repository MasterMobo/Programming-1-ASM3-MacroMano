import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Database {
    private ArrayList<Port> ports;
    private ArrayList<Trip> trips;
    private ArrayList<Vehicle> vehicles;


    private static LocalDate toLocalDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(dateString, formatter);
    }

    // TODO: is the date in question the arrival or departure day?
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
        // Returns all the trips departed between one day to another
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
