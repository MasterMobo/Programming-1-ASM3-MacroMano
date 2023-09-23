package PortSystem.DB;

import PortSystem.Port.Port;
import PortSystem.Trip.Trip;
import PortSystem.User.User;
import PortSystem.Utils.DBUtils;
import PortSystem.Utils.DisplayUtils;

import java.util.Scanner;

public class PortDatabase extends Database<Port> {

    public PortDatabase(MasterDatabase mdb) {
        super(mdb, "p");
    }

    public String showInfo(String portID) {
        Port foundPort = find(portID);
        return "Port{" +
                "\n     name='" + foundPort.getName() + '\'' + ", " +
                "\n     id='" + foundPort.getId() + '\'' + ", " +
                "\n     latitude=" + foundPort.getLat() + ", longitude=" + foundPort.getLon() + ", " +
                "\n     capacity=" + foundPort.getCapacity() + ", currentWeight=" + foundPort.getCurrentWeight() + ", " +
                "\n     isLanding=" + foundPort.isLanding() + ", " +
                "\n     containerCount=" + foundPort.getContainerCount() + ", vehicleCount=" + foundPort.getVehicleCount() +
                "\n}";
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
        return mdb.getContainers().fromPort(portId).size();
    }

    public int getVehicleCount(String portId) {
        return mdb.getContainers().fromVehicle(portId).size();
    }

}
