package PortManagementSystem.Vehicle;

import PortManagementSystem.Port;

import java.util.Scanner;

public class TankerTruck extends Truck {
    public TankerTruck(String name, Double carryCapacity, Double fuelCapacity) {
        super(name,
                carryCapacity,
                fuelCapacity);
    }

    public static TankerTruck createTankerTruck() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter tanker truck name:");
        String name = scanner.nextLine().trim();

        System.out.println("Enter carry capacity:");
        double carryCapacity = scanner.nextDouble();

        System.out.println("Enter fuel capacity:");
        double fuelCapacity = scanner.nextDouble();
        return new TankerTruck(name, carryCapacity, fuelCapacity);
    }
}