package PortSystem.Utils;

import java.util.ArrayList;

public class DisplayUtils {
    public static void printArray(ArrayList arr) {
        if (arr == null || arr.size() == 0) {
            DisplayUtils.printErrorMessage("No records found");
            return;
        }
        for (Object o : arr) {
            System.out.println(o);
        }
    }
    public static String formatDouble(double num) {
        return String.format("%.2f", num);
    }
    public static void printInvalidTypeError(String expectedTypes) {
        System.out.println(ConsoleColors.YELLOW + "Invalid Type. Expecting: " + expectedTypes + ConsoleColors.RESET);
    }

    public static void printSystemMessage(String msg) {
        System.out.println(ConsoleColors.BLUE + msg + ConsoleColors.RESET);
    }

    public static void printErrorMessage(String msg) {
        System.out.println(ConsoleColors.YELLOW + msg + ConsoleColors.RESET);
    }



}
