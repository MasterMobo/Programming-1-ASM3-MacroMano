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

    public static Ship createShip() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter ship name:");
        String name = scanner.nextLine().trim();

        System.out.println("Enter carry capacity:");
        double carryCapacity = scanner.nextDouble();

        System.out.println("Enter fuel capacity:");
        double fuelCapacity = scanner.nextDouble();
        return new Ship(name, carryCapacity, fuelCapacity);
    }
}