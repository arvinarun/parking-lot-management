package parking.location;

import parking.core.Vehicle;
import parking.core.ParkingLot;
import parking.core.ParkingSpace;
import parking.core.VehicleClass;

public class VehicleLocator {

    private ParkingLot parkingLot;

    public VehicleLocator(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }

    public String findVehicle(String vehicleNumber, String vehicleType) {

        if(vehicleNumber == null || vehicleNumber.trim().isEmpty()) {
            return "Invalid information !\nPlease enter a vehicle number.";
        }

        if(vehicleType == null || vehicleType.trim().isEmpty()) {
            return "Invalid information !\nPlease enter a vehicle type.";
        }

        vehicleNumber = vehicleNumber.trim();
        vehicleType = vehicleType.trim();

        VehicleClass vehicleClass = classifyVehicle(vehicleType);

        if(vehicleClass == null) {
            return "Invalid vehicle type !\nPlease try again.";
        }

        ParkingSpace space = parkingLot.findVehicle(vehicleNumber);

        if(space == null) {
            return "Vehicle not found !\nPlease check your vehicle number.";
        }

        Vehicle vehicle = space.getOccupiedVehicle();

        if(vehicle.getVehicleClass() != vehicleClass) {
            return "Vehicle not found !\nPlease check your vehicle number and type.";
        }

        int floor;

        if(space.getSpaceNumber() <= 50) {
            floor = 1;
        } else {
            floor = 2;
        }

        return "Vehicle Found.\n" +
                "Floor: " + floor +
                "\nParking Space: " + space.getSpaceNumber();
    }

    private VehicleClass classifyVehicle(String vehicleType) {

        switch(vehicleType.toLowerCase()) {

            case "suv":
            case "car":
            case "jeep":
            case "pickup truck":
            case "pickup":
                return VehicleClass.LIGHT;

            case "motorcycle":
            case "bike":
            case "motorbike":
                return VehicleClass.MOTORCYCLE;

            case "truck":
            case "bus":
                return VehicleClass.HEAVY;

            default:
                return null;
        }
    }
}