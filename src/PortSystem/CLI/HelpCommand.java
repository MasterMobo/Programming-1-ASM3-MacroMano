package PortSystem.CLI;

import PortSystem.DB.MasterDatabase;
import PortSystem.Utils.ConsoleColors;

public class HelpCommand extends Command{
    public HelpCommand() {
        super();
        signature = "help";
        desc = "List available commands";
        usage = "help";
        arguments = 0;
        loginRequired = false;
    }

    @Override
    public void execute(String[] arg, MasterDatabase db, CLI cli) {
        StringBuilder commandInfo = new StringBuilder();

        for (Command command: cli.getCommandMap().values()) {
            commandInfo.append(command.getInfo()).append("\n");
        }
        // TODO: add more commands for help
        System.out.println(ConsoleColors.CYAN_UNDERLINED + "Available Commands:\n" + ConsoleColors.RESET);
        System.out.println(commandInfo);
    }
}
