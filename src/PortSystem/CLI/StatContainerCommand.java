package PortSystem.CLI;

import PortSystem.DB.MasterDatabase;

public class StatContainerCommand extends Command{
    public StatContainerCommand() {
        signature = "statcon";
        desc = "Calculate container weight by type. Type 'all' for <portId> to search through all ports";
        usage = "statcon <portId>";
        arguments = 1;
    }

    public static void process(String[] args, MasterDatabase db) {
        StatContainerCommand cmd = new StatContainerCommand();
        if (!cmd.validateArguments(args)) {
            return;
        }

        cmd.execute(args, db);
    }

    public void execute(String[] args, MasterDatabase db) {
        if (args[0].equals("all")) {
            db.containers.listContainerTypeByWeight();
        } else {
            db.containers.listContainerTypeByWeight(args[0]);
        }
    }
}
