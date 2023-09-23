package PortSystem.Port;

import java.io.Serializable;
import java.util.Scanner;

import static java.lang.Math.*;

import PortSystem.Containers.*;
import PortSystem.DB.DatabaseRecord;
import PortSystem.Utils.DisplayUtils;

public class Port implements PortOperations, DatabaseRecord, Serializable {
    private String name;
    private String id;
    private double lat;
    private double lon;
    private double capacity;
    private double currentWeight;
    private boolean isLanding;

    public Port() {
    }

    public Port(String name, double lat, double lon, double capacity, boolean isLanding) {
        this.name = name;
        this.lat = lat;
        this.lon = lon;
        this.capacity = capacity;
        this.isLanding = isLanding;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {this.id = id;}

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public double getCapacity() {
        return capacity;
    }

    public double getCurrentWeight() {
        return currentWeight;
    }

    public boolean isLanding() {
        return isLanding;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public void setCurrentWeight(double currentWeight) {
        this.currentWeight = currentWeight;
    }

    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }

    @Override
    public double getDist(Port other) {
        // Calculates the distance between this port and the other port (in km)
        return acos(sin(this.lat)*sin(other.lat)+cos(this.lat)*cos(other.lat)*cos(other.lon-this.lon))*6371; // (6371 is Earth radius in km.)
    }

    @Override
    public void addContainer(Container c) {
        currentWeight += c.getWeight();
    }

    @Override
    public void removeContainer(Container c) {
        currentWeight -= c.getWeight();
    }

    @Override
    public boolean canAddContainer(Container c) {
        return c.getWeight() + currentWeight <= capacity;
    }

    public static Port createPort() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Creating new Port...");

        System.out.print("Enter Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Latitude: ");
        double lat = scanner.nextDouble();

        System.out.print("Enter Longitude: ");
        double lon = scanner.nextDouble();

        System.out.print("Enter Capacity: ");
        double capacity = scanner.nextDouble();

        System.out.print("Is it landing (true/false): ");
        boolean isLanding = scanner.nextBoolean();

        return new Port(name, lat, lon, capacity, isLanding);
    }

    @Override
    public String toString() {
        return "Port{" +
                name +
                " - " + id +
                ", location: " +
                "(" + lat + "°N, " +
                lon + "°E" + ")" +
                ", capacity=" + capacity +
                ", currentWeight=" + DisplayUtils.formatDouble(currentWeight) +
                ", isLanding=" + isLanding +
                '}';
    }
}
