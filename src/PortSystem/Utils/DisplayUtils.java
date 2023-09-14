package PortSystem.Utils;

import java.util.ArrayList;

public class DisplayUtils {
    public static void print(ArrayList arr) {
        if (arr == null) return;
        for (int i = 0; i < arr.size(); i++) {
            System.out.println(arr.get(i));
        }
    }
}
