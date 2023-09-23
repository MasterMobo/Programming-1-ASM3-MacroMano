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
        System.out.println(ConsoleColors.CYAN_UNDERLINED + "Available Commands:\n" + ConsoleColors.RESET
                + new HelpCommand().getInfo() + "\n"
                + new QuitCommand().getInfo() + "\n"
                + new LoginCommand().getInfo() + "\n"
                + new RegisterCommand().getInfo() + "\n"
                + new LogoutCommand().getInfo() + "\n"
                + new ShowUserInfoCommand().getInfo() + "\n"
                + new ListCommand().getInfo() + "\n"
                + new ListContainerFromVehicleCommand().getInfo() + "\n"
                + new ListContainerFromPortCommand().getInfo() + "\n"
                + new ListVehicleFromPortCommand().getInfo() + "\n"
                + new ListTripsFromPortCommand().getInfo() + "\n"
                + new ListTripsOnDaysCommand().getInfo() + "\n"
                + new FindCommand().getInfo() + "\n"
                + new CreateCommand().getInfo() + "\n"
                + new DeleteCommand().getInfo() + "\n"
                + new UpdateCommand().getInfo() + "\n"
                + new LoadContainerCommand().getInfo() + "\n"
                + new UnloadContainerCommand().getInfo() + "\n"
                + new VehicleNewTripCommand().getInfo() + "\n"
                + new UpdateTripStatusCommand().getInfo() + "\n"
                + new RefuelCommand().getInfo() + "\n"
                + new StatContainerCommand().getInfo() + "\n"
                + new StatFuelCommand().getInfo()
        );

    }
}
