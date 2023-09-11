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

    public ArrayList<Container> fromVehicle(String vehicleId, String type) {
        if (!vehicleExists(vehicleId)) return null;

        ArrayList<Container> res = new ArrayList<>();

        for (Map.Entry<String, Container> set: data.entrySet()) {
            Container container = set.getValue();
            if (Objects.equals(container.vehicleId, vehicleId) && container.getType().equals(type)) {
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

        if (vehicle.portId == null) {
            System.out.println("Vehicle is currently not in a port. Unable to load containers");
            return;
        }

        if (vehicle.getCurCarryWeight() == vehicle.getCarryCapacity()) {
            System.out.println("This vehicle is full, please choose a different one");
            return;
        }

        System.out.println("Container available for loading:");
        System.out.println(mdb.containers.fromPort(vehicle.portId));

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

           if(Objects.equals(container.vehicleId, vehicle.getId())) {
               System.out.println("Container is already on this vehicle");
               continue;
           }

           if (container.vehicleId != null) {
               System.out.println("Container already loaded on another vehicle");
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
            mdb.ports.find(container.portId).removeContainer(container);
            container.portId = null;
            vehicle.addWeight(container);
            mdb.save();
            // TODO message for successful add, delete loadedContainer + port attribute, consider if vehicle is in the port
            //  using curFuelConsumption was wrong, i removed it  - khoabui
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

            if(!mdb.containers.fromVehicle(vehicleId).contains(container)) {
                System.out.println("Container is not on this vehicle");
                continue;
            }

            container.vehicleId = null;
            vehicle.deductWeight(container);
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
