package PortSystem.CLI;

import PortSystem.DB.MasterDatabase;

public class QuitCommand extends Command{
    public QuitCommand() {
        signature = "!q";
        desc = "Quit program";
        usage = "!q";
        arguments = 0;
    }

    public static void process(String[] args, MasterDatabase db) {
    }
}
