package PortSystem.DB;

import PortSystem.Containers.*;
import PortSystem.Port.Port;
import PortSystem.Utils.DBUtils;
import PortSystem.Utils.DisplayUtils;
import PortSystem.Vehicle.*;

import java.io.Serializable;
import java.util.*;

public class ContainerDatabase extends Database<Container> implements Serializable {
    // Specialized class to store Container records

    public ContainerDatabase(MasterDatabase mdb) {
        super(mdb, "c");
    }

    public ArrayList<Container> fromVehicle(String vehicleId) {
        // Returns all containers in a given vehicle, null if no vehicle found

        Vehicle vehicle = mdb.getVehicles().find(vehicleId);
        if (vehicle == null) return null;

        ArrayList<Container> res = new ArrayList<>();

        for (Container container: data.values()) {
            if (Objects.equals(container.getVehicleId(), vehicleId)) {
                res.add(container);
            }
        }
        return res;
    }

    public ArrayList<Container> fromVehicle(String vehicleId, String type) {
        // Returns all containers (of a given type) in a given vehicle, null if no vehicle found

        Vehicle vehicle = mdb.getVehicles().find(vehicleId);
        if (vehicle == null) return null;

        ArrayList<Container> res = new ArrayList<>();

        for (Container container: data.values()) {
            if (Objects.equals(container.getVehicleId(), vehicleId) && Objects.equals(type, container.getType())) {
                res.add(container);
            }
        }
        return res;
    }


    public ArrayList<Container> fromPort(String pId) {
        // Returns all containers in a given port, null if no port found

        Port port = mdb.getPorts().find(pId);
        if (port == null) return null;

        ArrayList<Container> res = new ArrayList<>();

        for (Container container: data.values()) {
            if (Objects.equals(container.getPortId(), pId)) {
                res.add(container);
            }
        }

        return res;
    }

    public ArrayList<Container> fromPort(String pId, String type) {
        // Returns all containers (of a given type) in a given port, null if no port found

        Port port = mdb.getPorts().find(pId);
        if (port == null) return null;

        ArrayList<Container> res = new ArrayList<>();

        for (Container container: data.values()) {
            if (Objects.equals(pId,container.getPortId()) && Objects.equals(type, container.getType())) {
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

        showAllContainerInPort(vehicleId);
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

            if (!Objects.equals(container.getPortId(), vehicle.getPortId())) {
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


            if (!vehicle.canAddWeight(container)) {
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

        showAllContainerInVehicle(vehicleId);
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

            if(!fromVehicle(vehicleId).contains(container)) {
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

    public void showAllContainerInPort (String vehicleId) {
        Vehicle vehicle = mdb.getVehicles().find(vehicleId);
        if (vehicle == null) return;

        if (vehicle.getPortId() == null) {
            DisplayUtils.printErrorMessage("Vehicle is currently not in a port. Unable to load containers");
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

    public void printContainerWeightByType() {
        HashMap<String, Double> typeCount = Container.countContainerWeight(new ArrayList<>(data.values()));

        for (Map.Entry<String, Double> set: typeCount.entrySet()) {
            String containerType = set.getKey();
            if (Objects.equals(containerType, "Total Weight")) continue;

            double weight = set.getValue();

            System.out.println(containerType + ": " + weight + " (kg)");
        }

        System.out.println("Total Weight: " + typeCount.get("Total Weight") + "(kg)");

    }

    public void printContainerWeightByType(String portId) {
        ArrayList<Container> containers = fromPort(portId);

        if (containers == null) return;

        HashMap<String, Double> typeCount = Container.countContainerWeight(containers);
        for (Map.Entry<String, Double> set: typeCount.entrySet()) {
            String containerType = set.getKey();
            if (Objects.equals(containerType, "Total Weight")) continue;

            double weight = set.getValue();

            System.out.println(containerType + ": " + weight + " (kg)");
        }

        System.out.println("Total Weight: " + typeCount.get("Total Weight") + "(kg)");
    }

    public Container createRecord(Container container, String portId) {
        if (container == null) return null;
        Scanner scanner = new Scanner(System.in);

        Port p;

        if (portId == null) {
            System.out.print("Enter port ID: ");
            p = mdb.getPorts().find(scanner.nextLine().trim());
            if (p == null) return null;
        } else {
            p = mdb.getPorts().find(portId);
        }

        if (!p.canAddContainer(container)) {
            DisplayUtils.printErrorMessage("Port weight capacity exceeded");
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

    @Override
    public void showInfo(String containerId) {
        Container foundContainer = find(containerId);
        if (foundContainer == null) return;

        System.out.println("Container{" +
                "\n          Type: " + foundContainer.getType() + ", " +
                "\n          ID: '" + foundContainer.getId() + '\'' + ", " +
                "\n          Vehicle ID: " + foundContainer.getVehicleId() + ", Port ID: " + foundContainer.getPortId() + ", " +
                "\n          Weight: " + DisplayUtils.formatDouble(foundContainer.getWeight()) + " kg, " +
                "\n          Ship Fuel Consumption: " + DisplayUtils.formatDouble(foundContainer.getShipFuelConsumption()) + ", " +
                "\n          Truck Fuel Consumption: " + DisplayUtils.formatDouble(foundContainer.getTruckFuelConsumption()) +
                "\n          }");
    }
}
