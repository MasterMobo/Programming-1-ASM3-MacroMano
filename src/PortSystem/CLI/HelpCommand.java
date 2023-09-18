package PortSystem.CLI;

import PortSystem.DB.MasterDatabase;

public class HelpCommand extends Command{
    public HelpCommand() {
        signature = "help";
        desc = "List available commands";
        usage = "help";
        arguments = 0;
    }

    public static void process(String[] args, MasterDatabase db) {
    }
}
