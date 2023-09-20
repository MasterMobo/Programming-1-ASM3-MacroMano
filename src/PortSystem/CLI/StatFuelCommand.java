package PortSystem.CLI;

import PortSystem.DB.MasterDatabase;

public class StatFuelCommand extends Command{
    public StatFuelCommand() {
        super();
        signature = "statfuel";
        desc = "Calculate total fuel consumed for given day";
        usage = "statfuel <day>. Expecting dd/mm/yyy format";
        arguments = 1;
    }

    @Override
    public void execute(String[] args, MasterDatabase db, CLI cli) {
        double fuelConsumed = db.trips.fuelConsumed(args[0]);
        if (fuelConsumed < 0 ) return;
        System.out.println("Total fuel consumed on " + args[0] + ": " + fuelConsumed + "(gallons)");
    }
}
