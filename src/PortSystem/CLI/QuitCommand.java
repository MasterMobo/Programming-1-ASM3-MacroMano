package PortSystem.CLI;

import PortSystem.DB.MasterDatabase;

public class QuitCommand extends Command{
    public QuitCommand() {
        super();
        signature = "!q";
        desc = "Quit program";
        usage = "!q";
        arguments = 0;
    }

    @Override
    public void execute(String[] arg, MasterDatabase db, CLI cli) {

    }

}
