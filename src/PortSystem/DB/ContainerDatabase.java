package PortSystem.DB;

import PortSystem.CLI.CLI;
import PortSystem.Containers.*;
import PortSystem.Port.Port;
import PortSystem.Utils.ConsoleColors;
import PortSystem.Utils.DisplayUtils;
import PortSystem.Vehicle.*;

import java.io.Serializable;
import java.util.*;

public class ContainerDatabase extends Database<Container> implements Serializable {
    public ContainerDatabase(MasterDatabase mdb) {
        super(mdb, "c");
    }

    private boolean vehicleExists(String vehicleId) {
        return mdb.vehicles.exists(vehicleId);
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

    public ArrayList<Container> fromPort(String pId, String type) {
        Port port = mdb.ports.find(pId);
        if (port == null) return null;

        ArrayList<Container> res = new ArrayList<>();

        for (Map.Entry<String, Container> set: data.entrySet()) {
            Container container = set.getValue();
            if (Objects.equals(container.portId, pId)  && container.getType().equals(type)) {
                res.add(container);
            }
        }

        return res;

    }

    public void loadContainerOnVehicle(String vehicleId, String userPortId) {
        Vehicle vehicle = mdb.vehicles.find(vehicleId);
        if (vehicle == null) {
            return;
        }

        if (userPortId != null && !Objects.equals(vehicle.portId, userPortId)) {
            DisplayUtils.printErrorMessage("You do not have permission to this vehicle because it is not in your port");
            return;
        }

        if (vehicle.portId == null) {
            DisplayUtils.printErrorMessage("Vehicle is currently not in a port. Unable to load containers");
            return;
        }

        if (vehicle.getCurCarryWeight() >= vehicle.getCarryCapacity()) {
            DisplayUtils.printErrorMessage("This vehicle is full, please choose a different one");
            return;
        }

        mdb.containers.showAllContainerInPort(vehicleId);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter container ID (or 'exit' to stop): ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                break;
            }

            Container container = find(input);
            if (container == null) {
                continue;
            }

            if (!Objects.equals(container.portId, vehicle.portId)) {
                DisplayUtils.printErrorMessage("Container is not within this port");
                continue;
            }

           if(Objects.equals(container.vehicleId, vehicle.getId())) {
               DisplayUtils.printErrorMessage("Container is already on this vehicle");
               continue;
           }

           if (container.vehicleId != null) {
               DisplayUtils.printErrorMessage("Container already loaded on another vehicle");
               continue;
           }


            if (!vehicle.canAddContainer(container)) {
                DisplayUtils.printErrorMessage("Weight exceeded. The vehicle can not carry the specified container");
                continue;
            }

            if (!vehicle.allowToAdd(container)){
                DisplayUtils.printErrorMessage(vehicle.getType() + " can not carry " + container.getType() + " containers");
                continue;
            }

            container.vehicleId = vehicle.getId();
            mdb.ports.find(container.portId).removeContainer(container);
            container.portId = null;
            vehicle.addWeight(container);
            DisplayUtils.printSystemMessage("Successfully loaded to vehicle.");
            mdb.save();
        }
    }


    public void unloadFromVehicle(String vehicleId, String userPortId) {
        Vehicle vehicle = mdb.vehicles.find(vehicleId);
        if (vehicle == null) {
            DisplayUtils.printErrorMessage("There is no vehicle with the provided ID");
            return;
        }

        if (userPortId != null && !Objects.equals(vehicle.portId, userPortId)) {
            DisplayUtils.printErrorMessage("You do not have permission to this vehicle because it is not in your port");
            return;
        }

        if (vehicle.getCurCarryWeight() == 0 ) {
            DisplayUtils.printErrorMessage("There is no container on this vehicle");
            return;
        }

        Port port = mdb.ports.find(vehicle.portId);
        if (port == null) {
            DisplayUtils.printErrorMessage("Vehicle not currently in a port");
            return;
        }

        mdb.containers.showAllContainerInVehicle(vehicleId);
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter container ID (or 'exit' to stop): ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                break;
            }

            Container container = find(input);
            if (container == null) {
                continue;
            }

            if(!mdb.containers.fromVehicle(vehicleId).contains(container)) {
                DisplayUtils.printErrorMessage("Container is not on this vehicle");
                continue;
            }

            if (container.vehicleId == null) {
                DisplayUtils.printErrorMessage("Container currently not on a vehicle");
                continue;
            }

            if (!port.canAddContainer(container)) {
                DisplayUtils.printErrorMessage("Port weight capacity exceeded, can not add this container");
                continue;
            }

            port.addContainer(container);
            vehicle.deductWeight(container);
            container.portId = vehicle.portId;
            container.vehicleId = null;
            DisplayUtils.printSystemMessage("Successfully unloaded from vehicle");
            mdb.save();
        }

    }
// TODO do you even need this? just use find()

//    public void showInfo(String containerID) {
//        if (!containerExists(containerID)) {
//            System.out.println("Invalid Container ID");
//            return;
//        }
//        Container c = find(containerID);
//        System.out.println(c.getType() + ", id: " + c.getId() + ", weight: " + c.getWeight());
//    }

    public void showAllContainerInPort (String vehicleId) {
        Vehicle vehicle = mdb.vehicles.find(vehicleId);
        if (vehicle == null) return;

        if (vehicle.portId == null) {
            System.out.println("Vehicle is currently not in a port. Unable to load containers");
            return;
        }

        System.out.println("Available containers in this port: ");
        for (Container c: fromPort(vehicle.portId)) {
            if (c.vehicleId == null) System.out.println(c.getType() + ", id: " + c.getId() + ", weight: " + c.getWeight());
        };
    }

    public void showAllContainerInVehicle (String vehicleId) {
        Vehicle vehicle = mdb.vehicles.find(vehicleId);
        if (vehicle == null) return;

        System.out.println("Containers currently on this vehicle: ");
        for (Container c: fromVehicle(vehicleId)) {
            System.out.println(c.getType() + ", id: " + c.getId() + ", weight: " + c.getWeight());
        };
    }

    public void listContainerTypeByWeight() {
        HashMap<String, Double> typeCount = Container.countContainerWeight(new ArrayList<>(data.values()));
        for (Map.Entry<String, Double> set: typeCount.entrySet()) {
            String containerType = set.getKey();
            double weight = set.getValue();

            System.out.println(containerType + ": " + weight + " (kg)");
        }

    }

    public void listContainerTypeByWeight(String portId) {

        ArrayList<Container> containers = fromPort(portId);

        if (containers == null) return;

        HashMap<String, Double> typeCount = Container.countContainerWeight(containers);
        for (Map.Entry<String, Double> set: typeCount.entrySet()) {
            String containerType = set.getKey();
            double weight = set.getValue();

            System.out.println(containerType + ": " + weight + " (kg)");
        }
    }

    public Container createRecord(Container container, String portId) {
        if (container == null) return null;
        Scanner scanner = new Scanner(System.in);

        Port p = null;

        if (portId == null) {
            System.out.print("Enter port ID: ");
            p = mdb.ports.find(scanner.nextLine().trim());
            if (p == null) return null;
        } else {
            p = mdb.ports.find(portId);
        }

        if (!p.canAddContainer(container)) {
            System.out.println("Port weight capacity exceeded");
            return null;
        }

        container.portId = p.getId();
        p.addContainer(container);
        add(container);
        DisplayUtils.printSystemMessage("Created Record: " + container);
        return container;
    }

    @Override
    public Container updateRecord(String id) {
        Container container = super.updateRecord(id);
        if (container == null) return null;

        Scanner scanner = new Scanner(System.in);
        container.setWeight(getInputDouble("Weight: ", container.getWeight(), scanner));

        container.setShipFuelConsumption(getInputDouble("Ship Fuel Consumption: ", container.getShipFuelConsumption(), scanner));

        container.setTruckFuelConsumption(getInputDouble("Truck Fuel Consumption: ", container.getTruckFuelConsumption(), scanner));

        container.portId = getInputId("Port ID: ", container.portId, scanner, mdb.ports);

        container.vehicleId = getInputId("Vehicle ID: ", container.vehicleId, scanner, mdb.vehicles);

        DisplayUtils.printSystemMessage("Updated record: " + container);
        mdb.save();
        return container;
    }

    @Override
    public Container delete(String id) {
        Container deletedContainer = super.delete(id);
        if (deletedContainer == null) return null;

        if (mdb.ports.exists(deletedContainer.portId)) {
            Port port = mdb.ports.find(deletedContainer.portId);
            port.removeContainer(deletedContainer);
        }

        if (mdb.vehicles.exists(deletedContainer.vehicleId)) {
            Vehicle vehicle = mdb.vehicles.find(deletedContainer.vehicleId);
            vehicle.deductWeight(deletedContainer);
        }

        return deletedContainer;
    }
}
