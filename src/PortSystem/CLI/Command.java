package PortSystem.CLI;

public class Command {
    protected String signature;
    protected String desc;
    protected String usage;
    protected int arguments;

    protected boolean validateArguments(String[] args) {
        if (args.length != arguments) {
            printArgumentError(args.length);
            printUsage();
            return false;
        }
        return true;
    }



    public void printArgumentError(int received) {
        System.out.println("Expected " + arguments + " argument(s), but received " + received);
    }

    public String getInfo() {
        return usage + ": " + desc;
    }

    public void printUsage() {
        System.out.println("Usage: " + usage);
    }

    public String getDesc() {
        return desc;
    }

    public String getUsage() {
        return usage;
    }
}
