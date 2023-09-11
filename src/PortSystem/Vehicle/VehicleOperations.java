package PortSystem.Vehicle;

public interface VehicleOperations {
//    void loadContainer();
    void unloadContainer();
    boolean allowToTravel();
    void moveToPort();
    void refuel();
}