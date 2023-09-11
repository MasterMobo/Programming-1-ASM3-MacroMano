package PortSystem;

import java.io.Serializable;
import java.time.LocalDate;

import PortSystem.DB.DatabaseRecord;
import PortSystem.Port.Port;
import PortSystem.Utils.Status;
import PortSystem.Vehicle.Vehicle;

public class Trip implements DatabaseRecord, Serializable {
    private String id;
    private Vehicle vehicle;
    private LocalDate departDate;
    private LocalDate arriveDate;
    private Port departPort;
    private Port arrivePort;
    private double length;
    private Status status;

    public Trip() {
    }

    // TODO: Do you need to initialize status?
    public Trip(Vehicle vehicle, LocalDate departDate, LocalDate arriveDate, Port departPort, Port arrivePort) {
        this.vehicle = vehicle;
        this.departDate = departDate;
        this.arriveDate = arriveDate;
        this.departPort = departPort;
        this.arrivePort = arrivePort;
        this.status = Status.PROCESSING;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public LocalDate getArriveDate() {
        return arriveDate;
    }

    public LocalDate getDepartDate() {
        return departDate;
    }

    public Port getDepartPort() {
        return departPort;
    }

    public Port getArrivePort() {
        return arrivePort;
    }

    public double getLength() {
        return length;
    }

    public Status getStatus() {
        return status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "PortManagementSystem.Trip.PortManagementSystem.Trip{" +
                "vehicle=" + vehicle +
                ", departDate=" + departDate +
                ", arriveDate=" + arriveDate +
                ", departPort=" + departPort.getName() +
                ", arrivePort=" + arrivePort.getName() +
                ", length=" + length +
                ", status=" + status +
                '}';
    }
}
