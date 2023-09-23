package PortSystem.CLI;

import PortSystem.DB.MasterDatabase;

public class StatContainerCommand extends Command{
    public StatContainerCommand() {
        super();
        signature = "statcon";
        desc = "Calculate container weight by type. Type 'all' for <portId> to search through all ports";
        usage = "statcon <portId>";
        arguments = 1;
    }

    @Override
    public void execute(String[] args, MasterDatabase db, CLI cli) {
        if (args[0].equals("all")) {
            db.getContainers().listContainerTypeByWeight();
        } else {
            db.getContainers().listContainerTypeByWeight(args[0]);
        }
    }
}
