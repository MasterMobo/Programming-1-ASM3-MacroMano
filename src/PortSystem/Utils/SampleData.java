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

public class SampleData {
    public static MasterDatabase createSampleDatabase() {
        // TODO records can only be kept for 7 days, should sample data consider this?

        MasterDatabase db = new MasterDatabase();
        SystemAdmin admin = new SystemAdmin("admin", "123");
        db.users.add(admin);

        Port p1 = new Port("SGS", 122.2, 12.3, 3500, true);
        db.ports.add(p1);
        Port p2 = new Port("HN", 204.2, 56.5, 5000, true);
        db.ports.add(p2);
        Port p3 = new Port("DN", 16.0, 108.0, 3000, false );
        db.ports.add(p3);
        Port p4 = new Port("HP", 20.8, 106.7, 3000, false);
        db.ports.add(p4);
        Port p5 = new Port("CT", 10.1, 105.8, 3200, true);
        db.ports.add(p5);


        PortManager pm1 = new PortManager("SGS_manager", "saigon123hihi", p1.getId());
        db.users.add(pm1);
        PortManager pm2 = new PortManager("HN_manager", "hanoi456huhu", p2.getId());
        db.users.add(pm2);
        PortManager pm3 = new PortManager("DN_manager", "danang789haha", p3.getId());
        db.users.add(pm3);
        PortManager pm4 = new PortManager("HP_manager", "haiphongaki343", p4.getId());
        db.users.add(pm4);
        PortManager pm5 = new PortManager("CT_manager", "cantho357quetoi", p5.getId());
        db.users.add(pm5);

        // Create Ships:
        Ship ship1 = new Ship("Dakota-102", 5000.0, 2500.0);
        ship1.setPortId(p1.getId());
        p1.increaseVehicleCount();
        db.vehicles.add(ship1);

        Ship ship2 = new Ship("Yamato-234", 5000.0, 2500.0);
        ship2.setPortId(p2.getId());
        p2.increaseVehicleCount();
        db.vehicles.add(ship2);

        Ship ship3 = new Ship("BanaHill-781", 5500.0, 2500.0);
        ship3.setPortId(p3.getId());
        p3.increaseVehicleCount();
        db.vehicles.add(ship3);

        Ship ship4 = new Ship("AkiGun-124", 5500.0, 2500.0);
        ship4.setPortId(p4.getId());
        p4.increaseVehicleCount();
        db.vehicles.add(ship4);

        Ship ship5 = new Ship("NinhKieu-369", 5200.0, 2200.0);
        ship5.setPortId(p5.getId());
        p5.increaseVehicleCount();
        db.vehicles.add(ship5);

        //Create Trucks:
        Truck truck1 = new Truck("FuMiHung-556", 340.0, 300.0);
        truck1.setPortId(p1.getId());
        p1.increaseVehicleCount();
        db.vehicles.add(truck1);

        Truck truck2 = new Truck("OnePillar-231", 360.0, 300.0);
        truck2.setPortId(p2.getId());
        p2.increaseVehicleCount();
        db.vehicles.add(truck2);

        Truck truck3 = new Truck("CauRong-404", 340.0, 300.0);
        truck3.setPortId(p3.getId());
        p1.increaseVehicleCount();
        db.vehicles.add(truck3);

        Truck truck4 = new Truck("UnderTheSea-142", 320.0, 350.0);
        truck4.setPortId(p4.getId());
        p4.increaseVehicleCount();
        db.vehicles.add(truck4);

        Truck truck5 = new Truck("KienCuong-876", 320.0, 350.0);
        truck5.setPortId(p5.getId());
        p5.increaseVehicleCount();
        db.vehicles.add(truck5);

        //Create TankerTrucks:
        TankerTruck tt1 = new TankerTruck("Hulk-014", 1000.0, 500.0);
        tt1.setPortId(p1.getId());
        p1.increaseVehicleCount();
        db.vehicles.add(tt1);

        TankerTruck tt2 = new TankerTruck("IronMan-031", 1200.0, 600.0);
        tt2.setPortId(p2.getId());
        p2.increaseVehicleCount();
        db.vehicles.add(tt2);

        TankerTruck tt3 = new TankerTruck("CaptainAmerica-023", 1000.0, 500.0);
        tt3.setPortId(p1.getId());
        p3.increaseVehicleCount();
        db.vehicles.add(tt3);

        TankerTruck tt4 = new TankerTruck("Thor-134", 1000.0, 500.0);
        tt4.setPortId(p4.getId());
        p4.increaseVehicleCount();
        db.vehicles.add(tt4);

        TankerTruck tt5 = new TankerTruck("BlackWidow-314", 1000.0, 500.0);
        tt5.setPortId(p5.getId());
        p5.increaseVehicleCount();
        db.vehicles.add(tt5);

        //Create ReeferTrucks
        ReeferTruck rt1 = new ReeferTruck("Batman-504", 900.0, 600.0);
        rt1.setPortId(p1.getId());
        p1.increaseVehicleCount();
        db.vehicles.add(rt1);

        ReeferTruck rt2 = new ReeferTruck("Superman-321", 950.0, 650.0);
        rt2.setPortId(p2.getId());
        p2.increaseVehicleCount();
        db.vehicles.add(rt2);

        ReeferTruck rt3 = new ReeferTruck("WonderWoman-456", 850.0, 600.0);
        rt3.setPortId(p3.getId());
        p3.increaseVehicleCount();
        db.vehicles.add(rt3);

        ReeferTruck rt4 = new ReeferTruck("TheFlash-131", 850.0, 600.0);
        rt4.setPortId(p4.getId());
        p4.increaseVehicleCount();
        db.vehicles.add(rt4);

        ReeferTruck rt5 = new ReeferTruck("GreenLantern-122", 850.0, 600.0);
        rt5.setPortId(p5.getId());
        p5.increaseVehicleCount();
        db.vehicles.add(rt5);

        //Create Containers:
        // Containers loaded on vehicles
        DryStorage c1 = new DryStorage(12.4);
        c1.setVehicleId(ship1.getId());
        ship1.addWeight(c1);
        db.containers.add(c1);

        Liquid c2 = new Liquid(15.7);
        c2.setVehicleId(truck2.getId());
        truck2.addWeight(c2);
        db.containers.add(c2);

        OpenSide c3 = new OpenSide(10.2);
        c3.setVehicleId(tt3.getId());
        tt3.addWeight(c3);
        db.containers.add(c3);

        OpenTop c4 = new OpenTop(8.5);
        c4.setVehicleId(rt4.getId());
        rt4.addWeight(c4);
        db.containers.add(c4);

        Refrigerated c5 = new Refrigerated(18.9);
        c5.setVehicleId(ship5.getId());
        ship5.addWeight(c5);
        db.containers.add(c5);

        DryStorage c6 = new DryStorage(14.3);
        c6.setVehicleId(truck1.getId());
        truck1.addWeight(c6);
        db.containers.add(c6);

        Liquid c7 = new Liquid(20.0);
        c7.setVehicleId(tt4.getId());
        tt4.addWeight(c7);
        db.containers.add(c7);

        OpenSide c8 = new OpenSide(9.8);
        c8.setVehicleId(rt3.getId());
        rt3.addWeight(c8);
        db.containers.add(c8);

        OpenTop c9 = new OpenTop(7.2);
        c9.setVehicleId(ship2.getId());
        ship2.addWeight(c9);
        db.containers.add(c9);

        Refrigerated c10 = new Refrigerated(22.1);
        c10.setVehicleId(truck5.getId());
        truck5.addWeight(c10);
        db.containers.add(c10);

        // Containers on ports
        DryStorage c11 = new DryStorage(16.8);
        c11.setPortId(p3.getId());
        p3.addContainer(c11);
        db.containers.add(c11);

        Liquid c12 = new Liquid(13.6);
        c12.setPortId(p1.getId());
        p1.addContainer(c12);
        db.containers.add(c12);

        OpenSide c13 = new OpenSide(11.1);
        c13.setPortId(p5.getId());
        p5.addContainer(c13);
        db.containers.add(c13);

        OpenTop c14 = new OpenTop(9.3);
        c14.setPortId(p4.getId());
        p4.addContainer(c14);
        db.containers.add(c14);

        Refrigerated c15 = new Refrigerated(17.5);
        c15.setPortId(p2.getId());
        p2.addContainer(c15);
        db.containers.add(c15);

        DryStorage c16 = new DryStorage(14.7);
        c16.setPortId(p4.getId());
        p4.addContainer(c16);
        db.containers.add(c16);

        Liquid c17 = new Liquid(19.2);
        c17.setPortId(p2.getId());
        p2.addContainer(c17);
        db.containers.add(c17);

        OpenSide c18 = new OpenSide(8.4);
        c18.setPortId(p3.getId());
        p3.addContainer(c18);
        db.containers.add(c18);

        OpenTop c19 = new OpenTop(10.0);
        c19.setPortId(p5.getId());
        p5.addContainer(c19);
        db.containers.add(c19);

        Refrigerated c20 = new Refrigerated(21.3);
        c20.setPortId(p1.getId());
        p1.addContainer(c20);
        db.containers.add(c20);

        // Containers loaded on vehicles (continued)
        DryStorage c21 = new DryStorage(11.0);
        c21.setVehicleId(truck3.getId());
        truck3.addWeight(c21);
        db.containers.add(c21);

        Liquid c22 = new Liquid(14.8);
        c22.setVehicleId(tt2.getId());
        tt2.addWeight(c22);
        db.containers.add(c22);

        OpenSide c23 = new OpenSide(9.7);
        c23.setVehicleId(rt5.getId());
        rt5.addWeight(c23);
        db.containers.add(c23);

        OpenTop c24 = new OpenTop(8.1);
        c24.setVehicleId(ship3.getId());
        ship3.addWeight(c24);
        db.containers.add(c24);

        Refrigerated c25 = new Refrigerated(19.4);
        c25.setVehicleId(truck4.getId());
        truck4.addWeight(c25);
        db.containers.add(c25);

// Containers on ports (continued)
        DryStorage c26 = new DryStorage(15.2);
        c26.setPortId(p1.getId());
        p1.addContainer(c26);
        db.containers.add(c26);

        Liquid c27 = new Liquid(18.0);
        c27.setPortId(p3.getId());
        p3.addContainer(c27);
        db.containers.add(c27);

        OpenSide c28 = new OpenSide(10.5);
        c28.setPortId(p5.getId());
        p5.addContainer(c28);
        db.containers.add(c28);

        OpenTop c29 = new OpenTop(7.8);
        c29.setPortId(p2.getId());
        p2.addContainer(c29);
        db.containers.add(c29);

        Refrigerated c30 = new Refrigerated(20.7);
        c30.setPortId(p4.getId());
        p4.addContainer(c30);
        db.containers.add(c30);


        //Create Trips:
        Trip trip1 = new Trip(ship1.getId(), p1.getId(), p2.getId(), DateUtils.dayBeforeToday(5), DateUtils.dayBeforeToday(4), 1300, 655.4, TripStatus.FULFILLED);
        db.trips.add(trip1);

        Trip trip2 = new Trip(ship2.getId(), p2.getId(), p3.getId(), DateUtils.dayBeforeToday(4), DateUtils.dayBeforeToday(3), 1200, 620.2, TripStatus.FULFILLED);
        db.trips.add(trip2);

        Trip trip3 = new Trip(rt3.getId(), p3.getId(), p4.getId(), DateUtils.dayBeforeToday(3), DateUtils.dayBeforeToday(2), 1150, 590.0, TripStatus.FULFILLED);
        db.trips.add(trip3);

        Trip trip4 = new Trip(tt4.getId(), p4.getId(), p5.getId(), DateUtils.dayBeforeToday(2), DateUtils.dayBeforeToday(1), 1100, 560.5, TripStatus.FULFILLED);
        db.trips.add(trip4);

        Trip trip5 = new Trip(truck5.getId(), p5.getId(), p1.getId(), DateUtils.dayBeforeToday(1), DateUtils.dayBeforeToday(0), 1050, 530.2, TripStatus.FULFILLED);
        db.trips.add(trip5);

        Trip trip6 = new Trip(ship1.getId(), p1.getId(), p2.getId(), DateUtils.dayBeforeToday(7), DateUtils.dayBeforeToday(6), 1000, 499.8, TripStatus.FULFILLED);
        db.trips.add(trip6);

        Trip trip7 = new Trip(truck2.getId(), p2.getId(), p3.getId(), DateUtils.dayBeforeToday(6), DateUtils.dayBeforeToday(5), 950, 469.6, TripStatus.FULFILLED);
        db.trips.add(trip7);

        Trip trip8 = new Trip(rt3.getId(), p3.getId(), p4.getId(), DateUtils.dayBeforeToday(5), DateUtils.dayBeforeToday(4), 900, 439.4, TripStatus.FULFILLED);
        db.trips.add(trip8);

        Trip trip9 = new Trip(ship4.getId(), p4.getId(), p5.getId(), DateUtils.dayBeforeToday(4), DateUtils.dayBeforeToday(3), 850, 409.2, TripStatus.FULFILLED);
        db.trips.add(trip9);

        Trip trip10 = new Trip(ship5.getId(), p5.getId(), p1.getId(), DateUtils.dayBeforeToday(3), DateUtils.dayBeforeToday(2), 800, 379.0, TripStatus.FULFILLED);
        db.trips.add(trip10);

        Trip trip11 = new Trip(tt1.getId(), p1.getId(), p2.getId(), DateUtils.dayBeforeToday(7), DateUtils.dayBeforeToday(6), 750, 348.8, TripStatus.FULFILLED);
        db.trips.add(trip11);

        Trip trip12 = new Trip(ship2.getId(), p2.getId(), p3.getId(), DateUtils.dayBeforeToday(6), DateUtils.dayBeforeToday(5), 700, 318.6, TripStatus.FULFILLED);
        db.trips.add(trip12);

        Trip trip13 = new Trip(ship3.getId(), p3.getId(), p4.getId(), DateUtils.dayBeforeToday(5), DateUtils.dayBeforeToday(4), 650, 288.4, TripStatus.FULFILLED);
        db.trips.add(trip13);

        Trip trip14 = new Trip(ship4.getId(), p4.getId(), p5.getId(), DateUtils.dayBeforeToday(4), DateUtils.dayBeforeToday(3), 600, 258.2, TripStatus.FULFILLED);
        db.trips.add(trip14);

        Trip trip15 = new Trip(truck5.getId(), p5.getId(), p1.getId(), DateUtils.dayBeforeToday(3), DateUtils.dayBeforeToday(2), 550, 228.0, TripStatus.FULFILLED);
        db.trips.add(trip15);

        Trip trip16 = new Trip(ship1.getId(), p1.getId(), p2.getId(), DateUtils.dayBeforeToday(7), DateUtils.dayBeforeToday(6), 500, 197.8, TripStatus.FULFILLED);
        db.trips.add(trip16);

        Trip trip17 = new Trip(ship2.getId(), p2.getId(), p3.getId(), DateUtils.dayBeforeToday(6), DateUtils.dayBeforeToday(5), 450, 167.6, TripStatus.FULFILLED);
        db.trips.add(trip17);

        Trip trip18 = new Trip(rt3.getId(), p3.getId(), p4.getId(), DateUtils.dayBeforeToday(5), DateUtils.dayBeforeToday(4), 400, 137.4, TripStatus.FULFILLED);
        db.trips.add(trip18);

        Trip trip19 = new Trip(truck4.getId(), p4.getId(), p5.getId(), DateUtils.dayBeforeToday(4), DateUtils.dayBeforeToday(3), 350, 107.2, TripStatus.FULFILLED);
        db.trips.add(trip19);

        Trip trip20 = new Trip(ship5.getId(), p5.getId(), p1.getId(), DateUtils.dayBeforeToday(3), DateUtils.dayBeforeToday(2), 300, 77.0, TripStatus.FULFILLED);
        db.trips.add(trip20);

        Trip trip21 = new Trip(tt1.getId(), p2.getId(), p1.getId(), DateUtils.dayBeforeToday(2), DateUtils.dayAfterToday(3), 1300, 564.4, TripStatus.PROCESSING);
        db.trips.add(trip21);

        Trip trip22 = new Trip(tt2.getId(), p3.getId(), p2.getId(), DateUtils.dayBeforeToday(1), DateUtils.dayAfterToday(4), 1200, 530.2, TripStatus.PROCESSING);
        db.trips.add(trip22);

        Trip trip23 = new Trip(ship4.getId(), p4.getId(), p3.getId(), DateUtils.dayBeforeToday(0), DateUtils.dayAfterToday(5), 1150, 499.0, TripStatus.PROCESSING);
        db.trips.add(trip23);

        Trip trip24 = new Trip(ship5.getId(), p5.getId(), p4.getId(), DateUtils.dayBeforeToday(3), DateUtils.dayAfterToday(6), 1100, 470.5, TripStatus.PROCESSING);
        db.trips.add(trip24);

        Trip trip25 = new Trip(tt5.getId(), p1.getId(), p5.getId(), DateUtils.dayBeforeToday(4), DateUtils.dayAfterToday(7), 1050, 440.2, TripStatus.PROCESSING);
        db.trips.add(trip25);

        return db;
    }
}
