package parking.location;
import parking.core.Vehicle;
import parking.core.ParikngLot;
import parking.core.VehicleClass;

public class VehicleLocator
{
    private ParkingLot parkingLot;

    //Constructor 
    public VehicleLocator(ParkingLot parkingLot)
    {
        this.parkingLot = parkingLot;
    }
     //Inputs : vehicle number,vehicle type
     //Output : Vehicle location,Error message
   public String findVehicle(String vehicleNumber, String vehicleType)
   {
    //vehicle number validate
    if(vehicleNumber == null || vehicleNumber.trim().isEmpty())
    {
        return "Invalid information !\nplease enter a vehicle number.";
    }
    vehicleNumber = vehicleNumber.trim();
    //vehicle type validate
    if(vehicleType == null || vehicleType.trim().isEmpty())
    {
        return "Invalid information !\nplease enter a vehicle type.";
    }
    vehicleType = vehicleType.trim();
    //changing the vehicle type to the vehicle class
    VehicleClass vehicleClass = classifyVehicle(vehicleType);
    if(vehicleClass == null)
    {
        return "Invalid information !\nplease try again.";
    }
    //seaching for currently filled parking slots
    for(ParkingSpace space : parkingLot.getParkingSpaces())
    {
        if(space.isOccupied())
        {
                Vehicle vehicle = space.getVehicle();

                if(vehicle.getVehicleNumber().equalsIgnoreCase(vehicleNumber) && vehicle.getVehicleClass() == vehicleClass)
                {
                        return "Vehicle Found.\n"
                        + "Floor :" +space.getFloor()
                        + "\nParking space :"+space.getSpaceNumber();
                }
        }
    }
    //if there is no matching vehicle is found
    return "Vehicle not found !\n" + "Please check your Vehicle number and the type.";
    }
    private VehicleClass classifyVehicle(String vehicleType)
    {
        switch(vehicleType.toLowerCase())
        {
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