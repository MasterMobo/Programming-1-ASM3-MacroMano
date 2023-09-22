package PortSystem.Utils;

import PortSystem.Containers.*;
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


}
