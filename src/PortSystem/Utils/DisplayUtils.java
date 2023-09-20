/*Colored text: https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println*/

package PortSystem.Utils;

import java.util.ArrayList;

public class DisplayUtils {
    public static void printArray(ArrayList arr) {
        if (arr == null) return;
        for (Object o : arr) {
            System.out.println(o);
        }
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
