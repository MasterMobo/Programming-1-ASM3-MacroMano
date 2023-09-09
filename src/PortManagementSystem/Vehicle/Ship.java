package PortManagementSystem.Vehicle;
import PortManagementSystem.*;
import PortManagementSystem.DB.DatabaseRecord;

import java.util.Scanner;

public class Ship extends Vehicle {
    public Ship(String name, double carryCapacity, double fuelCapacity) {
        super(name,
                carryCapacity,
                fuelCapacity);
    }

}