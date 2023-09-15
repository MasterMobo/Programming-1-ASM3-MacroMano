package PortSystem.CLI;

public class VehicleMoveCommand extends Command{
    public VehicleMoveCommand() {
        signature = "vmc";
        desc = "Command to make vehicle embark the assigned trip";
        usage = "vmc <vId> <tId>";
        arguments = 2;
    }
}
