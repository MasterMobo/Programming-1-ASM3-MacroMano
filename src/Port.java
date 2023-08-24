import java.util.ArrayList;

import static java.lang.Math.*;

public class Port {
    private String name;
    private String id;
    private double lat;
    private double lon;
    private double capacity;
//    private double currentWeight;
    private boolean isLanding;
    private ArrayList<Container> containers;
    private ArrayList<Vehicle> vehicles;

    public Port() {
    }

    public Port(String name, String id, double lat, double lon, double capacity, boolean isLanding) {
        this.name = name;
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.capacity = capacity;
        this.isLanding = isLanding;
    }

    public double getDist(Port other) {
        // Calculates the distance between this port and the other port (in km)
        return acos(sin(this.lat)*sin(other.lat)+cos(this.lat)*cos(other.lat)*cos(other.lat-this.lat))*6371; // (6371 is Earth radius in km.)
    }
}
