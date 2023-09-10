package PortManagementSystem.DB;

import PortManagementSystem.Containers.*;
import PortManagementSystem.Port;
import PortManagementSystem.Vehicle.Vehicle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class ContainerDatabase extends Database<Container> implements Serializable {
    public ContainerDatabase(MasterDatabase mdb) {
        super(mdb, "c");
    }

    // TODO: Containers can be inside ports too, add portId to Container?

    private boolean vehicleExists(String vehicleId) {
        return mdb.vehicles.find(vehicleId) != null;
    }
    public ArrayList<Container> fromVehicle(String vehicleId) {
        if (!vehicleExists(vehicleId)) return null;

        ArrayList<Container> res = new ArrayList<>();

        for (Map.Entry<String, Container> set: data.entrySet()) {
            Container container = set.getValue();
            if (Objects.equals(container.vehicleId, vehicleId)) {
                res.add(container);
            }
        }
        return res;
    }

    public ArrayList<Container> dryStorageFromVehicle(String vehicleId) {
        if (!vehicleExists(vehicleId)) return null;

        ArrayList<Container> res = new ArrayList<>();

        for (Map.Entry<String, Container> set: data.entrySet()) {
            Container container = set.getValue();
            if (Objects.equals(container.vehicleId, vehicleId) && container instanceof DryStorage) {
                res.add(container);
            }
        }
        return res;
    }

    public ArrayList<Container> liquidFromVehicle(String vehicleId) {
        if (!vehicleExists(vehicleId)) return null;

        ArrayList<Container> res = new ArrayList<>();

        for (Map.Entry<String, Container> set: data.entrySet()) {
            Container container = set.getValue();
            if (Objects.equals(container.vehicleId, vehicleId) && container instanceof Liquid) {
                res.add(container);
            }
        }
        return res;
    }

    public ArrayList<Container> openSideFromVehicle(String vehicleId) {
        if (!vehicleExists(vehicleId)) return null;

        ArrayList<Container> res = new ArrayList<>();

        for (Map.Entry<String, Container> set: data.entrySet()) {
            Container container = set.getValue();
            if (Objects.equals(container.vehicleId, vehicleId) && container instanceof OpenSide) {
                res.add(container);
            }
        }
        return res;
    }

    public ArrayList<Container> openTopFromVehicle(String vehicleId) {
        if (!vehicleExists(vehicleId)) return null;

        ArrayList<Container> res = new ArrayList<>();

        for (Map.Entry<String, Container> set: data.entrySet()) {
            Container container = set.getValue();
            if (Objects.equals(container.vehicleId, vehicleId) && container instanceof OpenTop) {
                res.add(container);
            }
        }
        return res;
    }

    public ArrayList<Container> refridgeratedFromVehicle(String vehicleId) {
        if (!vehicleExists(vehicleId)) return null;

        ArrayList<Container> res = new ArrayList<>();

        for (Map.Entry<String, Container> set: data.entrySet()) {
            Container container = set.getValue();
            if (Objects.equals(container.vehicleId, vehicleId) && container instanceof Refridgerated) {
                res.add(container);
            }
        }
        return res;
    }

    @Override
    public Container createRecord(Container container) {
        if (container == null) return null;
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter port ID: ");
        Port p = mdb.ports.find(scanner.nextLine().trim());
        if (p == null) return null;

        if (!p.canAddContainer(container)) {
            System.out.println("Port weight capacity exceeded");
            return null;
        }

        container.portId = p.getId();
        p.addContainer(container);
        add(container);
        return container;
    }
}
