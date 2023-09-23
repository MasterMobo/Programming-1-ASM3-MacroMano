package PortSystem.DB;

import PortSystem.Containers.Container;
import PortSystem.Port.Port;
import PortSystem.Trip.Trip;
import PortSystem.User.User;
import PortSystem.Utils.DBUtils;
import PortSystem.Utils.DisplayUtils;
import PortSystem.Vehicle.Vehicle;

import java.util.ArrayList;
import java.util.Scanner;

public class PortDatabase extends Database<Port> {

    public PortDatabase(MasterDatabase mdb) {
        super(mdb, "p");
    }

    @Override
    public void showInfo(String portID) {
        Port foundPort = find(portID);
        if (foundPort == null) return;

        System.out.println( "Port{" +
                "\n     Name: " + foundPort.getName() + ", " +
                "\n     ID: " + foundPort.getId() + ", " +
                "\n     Latitude: " + DisplayUtils.formatDouble(foundPort.getLat()) + ", Longitude: " + DisplayUtils.formatDouble(foundPort.getLon()) + ", " +
                "\n     Capacity: " + DisplayUtils.formatDouble(foundPort.getCapacity())+ ", Current Weight: " + DisplayUtils.formatDouble(foundPort.getCurrentWeight()) + ", " +
                "\n     Landing: " + foundPort.isLanding() + ", " +
                "\n     Container Count: " + getContainerCount(portID) + ", Vehicle Count:" + getVehicleCount(portID) +
                "\n}");
    }

    @Override
    public Port updateRecord(String id) {
        Port port = super.updateRecord(id);
        if (port == null) return null;

        Scanner scanner = new Scanner(System.in);

        port.setName(DBUtils.getInputString("Name: ", port.getName(), scanner));

        port.setLat(DBUtils.getInputDouble("Latitude: ", port.getLat(), scanner));

        port.setLon(DBUtils.getInputDouble("Longitude: ", port.getLon(), scanner));

        port.setCapacity(DBUtils.getInputDouble("Capacity: ", port.getCapacity(), scanner));

        port.setCurrentWeight(DBUtils.getInputDouble("Current Weight: ", port.getCurrentWeight(), scanner));

        DisplayUtils.printSystemMessage("Updated record: " + port);

        mdb.save();
        return port;
    }

    public void printAllPorts() {
        data.values().forEach((Port p) -> System.out.println(p.getName() + " - " + p.getId()));
    }

    public int getContainerCount(String portId) {
        ArrayList<Container> containers =  mdb.getContainers().fromPort(portId);
        if (containers == null) return 0;
        return containers.size();
    }

    public int getVehicleCount(String portId) {
        ArrayList<Vehicle> vehicles = mdb.getVehicles().fromPort(portId);
        if (vehicles == null) return 0;
        return vehicles.size();
    }

}
