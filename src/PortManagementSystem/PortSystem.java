package PortManagementSystem;

import PortManagementSystem.CLI.CLI;
import PortManagementSystem.DB.MasterDatabase;

import java.util.Scanner;

public class PortSystem {
    public static void start() {
        MasterDatabase db = MasterDatabase.initDB();

        CLI cli = new CLI(db);
        System.out.println("COSC2081 GROUP ASSIGNMENT\n" +
                "CONTAINER PORT MANAGEMENT SYSTEM\n" +
                "Instructor: Mr. Minh Vu & Dr. Phong Ngo\n" +
                "Group: Nacro Mano\n" +
                "Tran Phan Trong Phuc - s3979081\n" +
                "Bui Dang Khoa - s3978482\n" +
                "Duong Tran Minh Hoang - s3978452\n" +
                "Nguyen Trong Tien - s3978616");
        System.out.println();
        System.out.println("Type 'login' or 'register' to begin!");
        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine().trim();
        while (!command.equals("!q")) {
            cli.executeCommand(command);

            command = scanner.nextLine().trim();

        }
    }

}
