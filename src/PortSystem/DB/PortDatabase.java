package PortSystem.DB;

import PortSystem.Port.Port;
import PortSystem.User.User;
import PortSystem.Utils.ConsoleColors;
import PortSystem.Utils.DisplayUtils;

import java.util.Scanner;

public class PortDatabase extends Database<Port> {

    public PortDatabase(MasterDatabase mdb) {
        super(mdb, "p");
    }
    private boolean portExists(String portId) {
        return mdb.ports.find(portId) != null;
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

        port.setName(getInputString("Name: ", port.getName(), scanner));

        port.setLat(getInputDouble("Latitude: ", port.getLat(), scanner));

        port.setLon(getInputDouble("Longitude: ", port.getLon(), scanner));

        port.setCapacity(getInputDouble("Capacity: ", port.getCapacity(), scanner));

        port.setCurrentWeight(getInputDouble("Current Weight: ", port.getCurrentWeight(), scanner));

        DisplayUtils.printSystemMessage("Updated record: " + port);

        mdb.save();
        return port;
    }


}
