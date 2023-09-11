package PortSystem.Vehicle;

import PortSystem.Containers.Container;
import PortSystem.Port.Port;

import java.util.ArrayList;

public interface VehicleOperations {
//    void loadContainer();
    void unloadContainer();
    boolean allowToTravel(float totalConsumption);
    void moveToPort(Port p1, Port p2, ArrayList<Container> containers);
    void refuel();
}