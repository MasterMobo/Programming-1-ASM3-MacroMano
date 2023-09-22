package PortSystem.Utils;

import PortSystem.Containers.*;
import PortSystem.DB.Database;
import PortSystem.DB.MasterDatabase;
import PortSystem.Port.Port;
import PortSystem.Trip.Trip;
import PortSystem.Trip.TripStatus;
import PortSystem.User.PortManager;
import PortSystem.User.SystemAdmin;
import PortSystem.Vehicle.ReeferTruck;
import PortSystem.Vehicle.Ship;
import PortSystem.Vehicle.TankerTruck;
import PortSystem.Vehicle.Truck;

import java.util.Random;
import java.util.Scanner;

public class DBUtils {
    public static String randKey(int length) {
        // Generate random key of specified length
        Random rnd = new Random();
        StringBuilder key = new StringBuilder();
        for (int i = 0; i < length; i++) {
            key.append(rnd.nextInt(10));
        }
        return key.toString();
    }

    public static String getInputString(String prompt, String currentVal, Scanner scanner) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        return input.equals(".") ? currentVal : input;
    }

    public static String getInputId(String prompt, String currentId, Scanner scanner, Database db) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        if (input.equals(".")) return currentId;

        if (input.equals("null")) return null;

        Object record = db.find(input);
        if (record == null) return currentId;

        return input;
    }

    public static double getInputDouble(String prompt, double currentVal, Scanner scanner) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        return input.equals(".") ? currentVal : Double.parseDouble(input);
    }

}
