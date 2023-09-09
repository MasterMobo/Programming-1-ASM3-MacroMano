package PortManagementSystem.Containers;

    public class DryStorage extends Container {

        public DryStorage(double weight) {
            super(weight);
            type = "Dry Storage";
            shipFuelConsumption = 3.5F;
            truckFuelConsumption = 4.6F;
        }
    }
