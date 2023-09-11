package PortManagementSystem.Vehicle;

import PortManagementSystem.Port;

import java.util.Scanner;

public class ReeferTruck extends Truck {
    public ReeferTruck(String name, Double carryCapacity, Double fuelCapacity) {
        super(name,
                carryCapacity,
                fuelCapacity);
        this.allowedContainers = new String[]{"Dry Storage", "Open Side", "Open Top", "Refrigerated"};
    }
}