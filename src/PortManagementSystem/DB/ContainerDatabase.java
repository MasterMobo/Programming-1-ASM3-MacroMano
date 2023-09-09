package PortManagementSystem.DB;

import PortManagementSystem.Containers.Container;
import PortManagementSystem.Containers.DryStorage;
import PortManagementSystem.Port;
import PortManagementSystem.Vehicle.Vehicle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class ContainerDatabase extends Database<Container> implements Serializable {
    public ContainerDatabase(MasterDatabase mdb) {
        super(mdb, "c");
    }

    public ArrayList<Container> fromVehicle(String vehicleId) {
        ArrayList<Container> res = new ArrayList<>();

        for (Map.Entry<String, Container> set: data.entrySet()) {
            Container container = set.getValue();
            if (Objects.equals(container.vehicleId, vehicleId)) {
                res.add(container);
            }
        }
        return res;
    }

}
