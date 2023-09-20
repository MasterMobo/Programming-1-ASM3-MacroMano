package PortSystem.CLI;

import PortSystem.DB.MasterDatabase;
import PortSystem.Utils.ConsoleColors;
import PortSystem.Utils.DisplayUtils;

public abstract class Command {
    protected String signature;
    protected String desc;
    protected String usage;
    protected int arguments;

    public Command() {
    }

    public Command(String signature, String desc, String usage, int arguments) {
        this.signature = signature;
        this.desc = desc;
        this.usage = usage;
        this.arguments = arguments;
    }

    public void process(String[] args, MasterDatabase db, CLI cli) {
        if (!validateArguments(args)) {
            return;
        }

        execute(args, db, cli);
    }

    public abstract void execute(String[] arg, MasterDatabase db, CLI cli);

    protected boolean validateArguments(String[] args) {
        if (args.length != arguments) {
            DisplayUtils.printErrorMessage("Expected " + arguments + " argument(s), but received " + args.length);
            DisplayUtils.printErrorMessage("Usage: " + usage);
            return false;
        }
        return true;
    }



    public String getInfo() {
        return ConsoleColors.BLUE_BOLD + usage + ": " + ConsoleColors.RESET +  desc;
    }

    public String getDesc() {
        return desc;
    }

    public String getUsage() {
        return usage;
    }
}
