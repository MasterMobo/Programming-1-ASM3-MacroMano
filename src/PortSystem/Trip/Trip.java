package PortSystem.Trip;

import java.io.Serializable;
import java.time.LocalDate;

import PortSystem.DB.DatabaseRecord;

public class Trip implements DatabaseRecord, Serializable {
    private String id;
    private String vehicleId;
    private String departPortId;
    private String arrivePortId;
    private LocalDate departDate;
    private LocalDate arriveDate;
    private double distance;
    private double fuelConsumed;
    private TripStatus status;

    public Trip(String vehicleId, String departPortId, String arrivePortId, LocalDate departDate, LocalDate arriveDate, double length, double fuelConsumed, TripStatus status) {
        this.vehicleId = vehicleId;
        this.departPortId = departPortId;
        this.arrivePortId = arrivePortId;
        this.departDate = departDate;
        this.arriveDate = arriveDate;
        this.distance = length;
        this.fuelConsumed = fuelConsumed;
        this.status = status;
    }

    public LocalDate getArriveDate() {
        return arriveDate;
    }

    public LocalDate getDepartDate() {
        return departDate;
    }

    public double getDistance() {
        return distance;
    }

    public TripStatus getStatus() {
        return status;
    }
    public double getFuelConsumed () { return fuelConsumed;}

    public String getVehicleId() {
        return vehicleId;
    }

    public String getDepartPortId() {
        return departPortId;
    }

    public String getArrivePortId() {
        return arrivePortId;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setStatus(TripStatus status) {
        this.status = status;
    }

    public void setArriveDate(LocalDate date) {
        arriveDate = date;
    }

    @Override
    public String toString() {
        return "Trip{" +
                id +
                ", vehicleId='" + vehicleId + '\'' +
                ", from port " + departPortId +
                " to port " + arrivePortId +
                ", departDate=" + departDate +
                ", arriveDate=" + arriveDate +
                ", distance=" + distance +
                ", fuelConsumed=" + fuelConsumed +
                ", status=" + status +
                '}';
    }
}
