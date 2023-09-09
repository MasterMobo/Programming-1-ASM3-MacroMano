package PortManagementSystem.Vehicle;

import PortManagementSystem.Port;

import java.util.Scanner;

public class ReeferTruck extends Truck {
    public ReeferTruck(String name, Double carryCapacity, Double fuelCapacity) {
        super(name,
                carryCapacity,
                fuelCapacity);
    }

    public static ReeferTruck createReeferTruck() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter reefer truck name:");
        String name = scanner.nextLine().trim();

        System.out.println("Enter carry capacity:");
        double carryCapacity = scanner.nextDouble();

        System.out.println("Enter fuel capacity:");
        double fuelCapacity = scanner.nextDouble();
        return new ReeferTruck(name, carryCapacity, fuelCapacity);
    }
}