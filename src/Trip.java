import java.util.Date;

import Utils.Status;

public class Trip {
    private Vehicle vehicle;
    private Date departDate;
    private Date arriveDate;
    private Port departPort;
    private Port arrivePort;
    private double length;
    private Status status;

    public Trip() {
    }

    // TODO: Do you need to initialize status?
    public Trip(Vehicle vehicle, Date departDate, Date arriveDate, Port departPort, Port arrivePort) {
        this.vehicle = vehicle;
        this.departDate = departDate;
        this.arriveDate = arriveDate;
        this.departPort = departPort;
        this.arrivePort = arrivePort;
        this.status = Status.PROCESSING;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
