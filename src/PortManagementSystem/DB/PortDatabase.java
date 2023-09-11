package PortManagementSystem.DB;

import PortManagementSystem.Port.Port;

import java.util.Scanner;

public class PortDatabase extends Database<Port> {

    public PortDatabase(MasterDatabase mdb) {
        super(mdb, "p");
    }

    @Override
    public Port updateRecord(String id) {
        Port port = super.updateRecord(id);

        Scanner scanner = new Scanner(System.in);

        port.setName(getInputString("Name: ", port.getName(), scanner));

        port.setLat(getInputDouble("Latitude: ", port.getLat(), scanner));

        port.setLon(getInputDouble("Longitude: ", port.getLon(), scanner));

        port.setCapacity(getInputDouble("Capacity: ", port.getCapacity(), scanner));

        port.setCurrentWeight(getInputDouble("Current Weight: ", port.getCurrentWeight(), scanner));

        System.out.println("Updated record: " + port);

        mdb.save();
        return port;
    }


}
