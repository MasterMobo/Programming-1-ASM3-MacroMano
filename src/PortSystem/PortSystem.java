package PortSystem;

import PortSystem.CLI.CLI;
import PortSystem.DB.MasterDatabase;
import PortSystem.Exceptions.CommandNotFoundException;
import PortSystem.Utils.ConsoleColors;
import PortSystem.Utils.DisplayUtils;

import java.util.InputMismatchException;
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

        System.out.println(ConsoleColors.BLUE_BOLD + "Welcome to PORTIFY!" + ConsoleColors.RESET);
        System.out.println("Type 'login' or 'register' to begin");

        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine().trim();
        while (!command.equals("!q")) {
            try {
                cli.executeCommand(command);
            }
            catch (InputMismatchException e) {
                DisplayUtils.printErrorMessage("Invalid Input");
            }
            catch (CommandNotFoundException e) {
                DisplayUtils.printErrorMessage(e.getMessage());
            }
            catch (NumberFormatException e) {
                DisplayUtils.printErrorMessage("Invalid Input");
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            finally {
                command = scanner.nextLine().trim();
            }
        }

    }

}
