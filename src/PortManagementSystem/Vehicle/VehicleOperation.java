package PortManagementSystem.Vehicle;

import PortManagementSystem.Containers.Container;

import java.util.ArrayList;

public interface VehicleOperation {
    void loadContainer();
    void unloadContainer();
    boolean allowToTravel();
//    boolean moveToPort();
//    void refuel();
}