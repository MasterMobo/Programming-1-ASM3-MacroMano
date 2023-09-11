package PortSystem.DB;

import PortSystem.Containers.*;
import PortSystem.Port.Port;
import PortSystem.Vehicle.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class ContainerDatabase extends Database<Container> implements Serializable {
    public ContainerDatabase(MasterDatabase mdb) {
        super(mdb, "c");
    }

    private boolean vehicleExists(String vehicleId) {
        return mdb.vehicles.find(vehicleId) != null;
    }

    private boolean containerExists(String containerID) {return mdb.containers.find(containerID) != null;}

    public Container getContainerFromPort (String containerID, String portId){
        if (!containerExists(containerID)) return null;
        ArrayList<Container> containers = fromPort(portId);

        for (Container container : containers) {
            if (container.getId().equals(containerID)) {
                return container;
            }
        }
        return null;
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
            if (Objects.equals(container.vehicleId, vehicleId) && container instanceof Refrigerated) {
                res.add(container);
            }
        }
        return res;
    }

    public ArrayList<Container> fromPort(String pId) {
        Port port = mdb.ports.find(pId);
        if (port == null) return null;

        ArrayList<Container> res = new ArrayList<>();

        for (Map.Entry<String, Container> set: data.entrySet()) {
            Container container = set.getValue();
            if (Objects.equals(container.portId, pId)) {
                res.add(container);
            }
        }

        return res;
    }

    public void loadContainerOnVehicle(String vehicleId) {
        Vehicle vehicle = mdb.vehicles.find(vehicleId);
        if (vehicle == null) return;

        if (vehicle.getCurCarryWeight() == vehicle.getCarryCapacity()) {
            System.out.println("This vehicle is full, please choose a different one");
            return;
        }
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter container ID (or 'exit' to stop): ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                break;
            }

            Container container = find(input);
            if (container == null) {
                System.out.println("No container object associated with the provided ID.");
                continue;
            }


            if (!vehicle.canAddContainer(container)) {
                System.out.println("Weight exceeded. The vehicle can not carry the specified container");
                continue;
            }

            if (!vehicle.allowToAdd(container)){
                System.out.println(vehicle.getType() + " can not carry " + container.getType() + " containers");
                continue;
            }

            container.vehicleId = vehicle.getId();
            vehicle.addWeight(container);
            vehicle.addFuelConsumption(container);
            vehicle.addContainerToList(container);
            mdb.save();
            // TODO message for successful add, delete loadedContainer + port attribute, consider if vehicle is in the port
            // TODO check if container is already on another vehicle (or on this vehicle)
            // TODO maybe print all the containers from the port of the vehicle. Only allow users to choose from those containers?
            // TODO set portID to null when loaded on vehicle?
        }
    }

    public void unloadFromVehicle(String vehicleId) {
        Vehicle vehicle = mdb.vehicles.find(vehicleId);
        if (vehicle == null) return;

        if (vehicle.getCurCarryWeight() == 0 ) {
            System.out.println("There is no container on this vehicle");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter container ID (or 'exit' to stop): ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                break;
            }

            Container container = find(input);
            if (container == null) {
                System.out.println("No container object associated with the provided ID.");
                continue;
            }

            if (!vehicle.getLoadedContainers().contains(container)) {
                System.out.println("Container not found");
                continue;
            }

            container.vehicleId = null;
            vehicle.deductWeight(container);
            vehicle.deductFuelConsumption(container);
            vehicle.removeContainerFromList(container);
            mdb.save();
        }

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
        p.increaseContainerCount();
        add(container);
        System.out.println("Created Record: " + container);
        return container;
    }

    @Override
    public Container updateRecord(String id) {
        Container container = super.updateRecord(id);

        Scanner scanner = new Scanner(System.in);
        container.setWeight(getInputDouble("Weight: ", container.getWeight(), scanner));

        container.setShipFuelConsumption(getInputDouble("Ship Fuel Consumption: ", container.getShipFuelConsumption(), scanner));

        container.setTruckFuelConsumption(getInputDouble("Truck Fuel Consumption: ", container.getTruckFuelConsumption(), scanner));

        System.out.println("Updated record: " + container);
        mdb.save();
        return container;
    }
}
