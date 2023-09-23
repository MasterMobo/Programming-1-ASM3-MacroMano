package PortSystem.Vehicle;

import PortSystem.Containers.Container;
import PortSystem.Port.Port;

import java.util.ArrayList;

public interface VehicleOperations {
    boolean allowToAdd(Container c);
    boolean canAddWeight(Container c);
    void addWeight(Container c);
    void deductWeight(Container c);
    void deductFuel(double consumeAmount);
    double calculateTotalConsumption(Port p1, Port p2, ArrayList<Container> containers);
}