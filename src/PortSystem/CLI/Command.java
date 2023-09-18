package PortSystem.CLI;

import PortSystem.Utils.ConsoleColors;
import PortSystem.Utils.DisplayUtils;

public class Command {
    protected String signature;
    protected String desc;
    protected String usage;
    protected int arguments;

    protected boolean validateArguments(String[] args) {
        if (args.length != arguments) {
            DisplayUtils.printErrorMessage("Expected " + arguments + " argument(s), but received " + args.length);
            DisplayUtils.printErrorMessage("Usage: " + usage);
            return false;
        }
        return true;
    }



    public void printArgumentError(int received) {
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
