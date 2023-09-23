package PortSystem.DB;

import PortSystem.Port.Port;
import PortSystem.User.User;
import PortSystem.Utils.DBUtils;
import PortSystem.Utils.DisplayUtils;

import java.util.Scanner;

public class PortDatabase extends Database<Port> {

    public PortDatabase(MasterDatabase mdb) {
        super(mdb, "p");
    }
    // TODO do you even need this? just use find()

//    public void showInfo(String portID) {
//        if (!portExists(portID)) {
//            System.out.println("Invalid Port ID");
//            return;
//        }
//        Port foundPort = find(portID);
//        System.out.println(foundPort.toString());
//    }

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
        return mdb.getContainers().fromPort(portId).size();
    }

    public int getVehicleCount(String portId) {
        return mdb.getContainers().fromVehicle(portId).size();
    }

}
