package PortSystem.DB;

import PortSystem.CLI.CLI;
import PortSystem.Containers.*;
import PortSystem.Port.Port;
import PortSystem.Utils.ConsoleColors;
import PortSystem.Utils.DBUtils;
import PortSystem.Utils.DisplayUtils;
import PortSystem.Vehicle.*;

import java.io.Serializable;
import java.util.*;

public class ContainerDatabase extends Database<Container> implements Serializable {
    public ContainerDatabase(MasterDatabase mdb) {
        super(mdb, "c");
    }

    private boolean vehicleExists(String vehicleId) {
        return mdb.getVehicles().exists(vehicleId);
    }

    public ArrayList<Container> fromVehicle(String vehicleId) {
        if (!vehicleExists(vehicleId)) return null;

        ArrayList<Container> res = new ArrayList<>();

        for (Map.Entry<String, Container> set: data.entrySet()) {
            Container container = set.getValue();
            if (Objects.equals(container.getVehicleId(), vehicleId)) {
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
            if (Objects.equals(container.getVehicleId(), vehicleId) && container.getType().equals(type)) {
                res.add(container);
            }
        }
        return res;
    }


    public ArrayList<Container> fromPort(String pId) {
        Port port = mdb.getPorts().find(pId);
        if (port == null) return null;

        ArrayList<Container> res = new ArrayList<>();

        for (Map.Entry<String, Container> set: data.entrySet()) {
            Container container = set.getValue();
            if (container.getPortId().equals(pId)) {
                res.add(container);
            }
        }

        return res;

    }

    public ArrayList<Container> fromPort(String pId, String type) {
        Port port = mdb.getPorts().find(pId);
        if (port == null) return null;

        ArrayList<Container> res = new ArrayList<>();

        for (Map.Entry<String, Container> set: data.entrySet()) {
            Container container = set.getValue();
            if (container.getPortId().equals(pId) && container.getType().equals(type)) {
                res.add(container);
            }
        }

        return res;

    }

    public void loadContainerOnVehicle(String vehicleId, String userPortId) {
        Vehicle vehicle = mdb.getVehicles().find(vehicleId);
        if (vehicle == null) {
            return;
        }

        if (userPortId != null && !Objects.equals(vehicle.getPortId(), userPortId)) {
            DisplayUtils.printErrorMessage("You do not have permission to this vehicle because it is not in your port");
            return;
        }

        if (vehicle.getPortId() == null) {
            DisplayUtils.printErrorMessage("Vehicle is currently not in a port. Unable to load containers");
            return;
        }

        if (vehicle.getCurCarryWeight() >= vehicle.getCarryCapacity()) {
            DisplayUtils.printErrorMessage("This vehicle is full, please choose a different one");
            return;
        }

        mdb.getContainers().showAllContainerInPort(vehicleId);
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

            if (!container.getPortId().equals(vehicle.getPortId())) {
                DisplayUtils.printErrorMessage("Container is not within this port");
                continue;
            }

           if(Objects.equals(container.getVehicleId(), vehicle.getId())) {
               DisplayUtils.printErrorMessage("Container is already on this vehicle");
               continue;
           }

           if (container.getVehicleId() != null) {
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


            container.setVehicleId(vehicle.getId());
            mdb.getPorts().find(container.getPortId()).removeContainer(container);
            container.setPortId(null);

           
            vehicle.addWeight(container);
            DisplayUtils.printSystemMessage("Successfully loaded to vehicle.");
            mdb.save();
        }
    }


    public void unloadFromVehicle(String vehicleId, String userPortId) {
        Vehicle vehicle = mdb.getVehicles().find(vehicleId);
        if (vehicle == null) {
            DisplayUtils.printErrorMessage("There is no vehicle with the provided ID");
            return;
        }

        if (userPortId != null && !Objects.equals(vehicle.getPortId(), userPortId)) {
            DisplayUtils.printErrorMessage("You do not have permission to this vehicle because it is not in your port");
            return;
        }

        if (vehicle.getCurCarryWeight() == 0 ) {
            DisplayUtils.printErrorMessage("There is no container on this vehicle");
            return;
        }

        Port port = mdb.getPorts().find(vehicle.getPortId());

        if (port == null) {
            DisplayUtils.printErrorMessage("Vehicle not currently in a port");
            return;
        }

        mdb.getContainers().showAllContainerInVehicle(vehicleId);
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

            if(!mdb.getContainers().fromVehicle(vehicleId).contains(container)) {
                DisplayUtils.printErrorMessage("Container is not on this vehicle");
                continue;
            }

            if (container.getVehicleId() == null) {
                DisplayUtils.printErrorMessage("Container currently not on a vehicle");
                continue;
            }

            if (!port.canAddContainer(container)) {
                DisplayUtils.printErrorMessage("Port weight capacity exceeded, can not add this container");
                continue;
            }

            port.addContainer(container);
            vehicle.deductWeight(container);
            container.setPortId(vehicle.getPortId());
            container.setVehicleId(null);
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
        Vehicle vehicle = mdb.getVehicles().find(vehicleId);
        if (vehicle == null) return;

        if (vehicle.getPortId() == null) {
            System.out.println("Vehicle is currently not in a port. Unable to load containers");
            return;
        }

        System.out.println("Available containers in this port: ");
        for (Container c: fromPort(vehicle.getPortId())) {
            if (c.getVehicleId() == null) System.out.println(c.getType() + ", id: " + c.getId() + ", weight: " + c.getWeight());
        };
    }

    public void showAllContainerInVehicle (String vehicleId) {
        Vehicle vehicle = mdb.getVehicles().find(vehicleId);
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
            p = mdb.getPorts().find(scanner.nextLine().trim());
            if (p == null) return null;
        } else {
            p = mdb.getPorts().find(portId);
        }

        if (!p.canAddContainer(container)) {
            System.out.println("Port weight capacity exceeded");
            return null;
        }

        container.setPortId(p.getId());
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
        container.setWeight(DBUtils.getInputDouble("Weight: ", container.getWeight(), scanner));

        container.setShipFuelConsumption(DBUtils.getInputDouble("Ship Fuel Consumption: ", container.getShipFuelConsumption(), scanner));

        container.setTruckFuelConsumption(DBUtils.getInputDouble("Truck Fuel Consumption: ", container.getTruckFuelConsumption(), scanner));


        container.setPortId(DBUtils.getInputId("Port ID: ", container.getPortId(), scanner, mdb.getPorts()));

        container.setVehicleId(DBUtils.getInputId("Vehicle ID: ", container.getVehicleId(), scanner, mdb.getVehicles()));


        DisplayUtils.printSystemMessage("Updated record: " + container);
        mdb.save();
        return container;
    }

    @Override
    public Container delete(String id) {
        Container deletedContainer = super.delete(id);
        if (deletedContainer == null) return null;


        if (mdb.getPorts().exists(deletedContainer.getPortId())) {
            Port port = mdb.getPorts().find(deletedContainer.getPortId());
            port.removeContainer(deletedContainer);
        }

        if (mdb.getVehicles().exists(deletedContainer.getVehicleId())) {
            Vehicle vehicle = mdb.getVehicles().find(deletedContainer.getVehicleId());

            vehicle.deductWeight(deletedContainer);
        }

        return deletedContainer;
    }
}
