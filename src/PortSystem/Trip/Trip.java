package PortSystem.Trip;

import java.io.Serializable;
import java.time.LocalDate;

import PortSystem.DB.DatabaseRecord;
import PortSystem.Port.Port;
import PortSystem.Vehicle.Vehicle;

public class    Trip implements DatabaseRecord, Serializable {
    private String id;
    public String vehicleId;
    public String departPortId;
    public String arrivePortId;
    private LocalDate departDate;
    private LocalDate arriveDate;
    private double length;
    private double fuelConsumed;
    private TripStatus status;


    public Trip(String vehicleId, String departPortId, String arrivePortId, LocalDate departDate, LocalDate arriveDate, double length, double fuelConsumed, TripStatus status) {
        this.vehicleId = vehicleId;
        this.departPortId = departPortId;
        this.arrivePortId = arrivePortId;
        this.departDate = departDate;
        this.arriveDate = arriveDate;
        this.length = length;
        this.fuelConsumed = fuelConsumed;
        this.status = status;
    }

    public LocalDate getArriveDate() {
        return arriveDate;
    }

    public LocalDate getDepartDate() {
        return departDate;
    }

    public double getLength() {
        return length;
    }

    public TripStatus getStatus() {
        return status;
    }
    public double getFuelConsumed () { return fuelConsumed;}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setStatus(TripStatus status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return "Trip{" +
                "id='" + id + '\'' +
                ", vehicleId='" + vehicleId + '\'' +
                ", departPortId='" + departPortId + '\'' +
                ", arrivePortId='" + arrivePortId + '\'' +
                ", departDate=" + departDate +
                ", arriveDate=" + arriveDate +
                ", length=" + length +
                ", fuelConsumed=" + fuelConsumed +
                ", status=" + status +
                '}';
    }
}
