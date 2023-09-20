package PortSystem.CLI;

import PortSystem.DB.MasterDatabase;

public class HelpCommand extends Command{
    public HelpCommand() {
        super();
        signature = "help";
        desc = "List available commands";
        usage = "help";
        arguments = 0;
    }

    @Override
    public void execute(String[] arg, MasterDatabase db, CLI cli) {
    }
}
