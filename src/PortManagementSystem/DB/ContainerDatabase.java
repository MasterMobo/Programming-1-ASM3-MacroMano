package PortManagementSystem.DB;

import PortManagementSystem.Containers.Container;
import PortManagementSystem.Containers.DryStorage;
import PortManagementSystem.Port;
import PortManagementSystem.Vehicle.Vehicle;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class ContainerDatabase extends Database<Container> {
    public ContainerDatabase() {
        super("c");
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

    //TODO: testing
    public static void main(String[] args) {
        MasterDatabase db = new MasterDatabase();
        Port p1 = new Port("SGS", 122.2, 12.3, 647, true);
        db.ports.add(p1);

        Vehicle v1 = new Vehicle("Shit", "piss", p1, 12., 23.);
        db.vehicles.add(v1);

        DryStorage c = new DryStorage("ass", 12.3);
        c.vehicleId = v1.getId();
        db.containers.add(c);
        System.out.println(db.containers.fromVehicle(v1.getId()));
    }
}
