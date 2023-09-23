package PortSystem.CLI;

import PortSystem.DB.MasterDatabase;
import PortSystem.Utils.ConsoleColors;
import PortSystem.Utils.DisplayUtils;

public abstract class Command {
    protected String signature;
    protected String desc;
    protected String usage;
    protected int arguments;
    protected boolean loginRequired;

    public Command() {
    }

    public Command(String signature, String desc, String usage, int arguments, boolean loginRequired) {
        this.signature = signature;
        this.desc = desc;
        this.usage = usage;
        this.arguments = arguments;
        this.loginRequired = loginRequired;
    }

    public void process(String[] args, MasterDatabase db, CLI cli) {
        if (loginRequired && !cli.isLoggedIn()) {
            DisplayUtils.printErrorMessage("Please login before continue");
            return;
        }

        if (!validArguments(args)) {
            DisplayUtils.printErrorMessage("Expected " + arguments + " argument(s), but received " + args.length);
            DisplayUtils.printErrorMessage("Usage: " + usage);
            DisplayUtils.printErrorMessage("Description: " + desc);
            return;
        }

        execute(args, db, cli);
    }

    public abstract void execute(String[] arg, MasterDatabase db, CLI cli);

    protected boolean validArguments(String[] args) {
        return args.length == arguments;
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
